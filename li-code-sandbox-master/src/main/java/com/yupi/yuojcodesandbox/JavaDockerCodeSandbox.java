package com.yupi.yuojcodesandbox;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.*;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.yupi.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yupi.yuojcodesandbox.model.ExecuteCodeResponse;
import com.yupi.yuojcodesandbox.model.ExecuteMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JavaDockerCodeSandbox extends JavaCodeSandboxTemplate {
    private static final long TIME_OUT = 5000L;

    private static final Boolean FIRST_INIT = Boolean.FALSE;

//    public static void main(String[] args) {
//        JavaDockerCodeSandbox javaNativeCodeSandbox = new JavaDockerCodeSandbox();
//        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
//        executeCodeRequest.setInputList(Arrays.asList("1 2", "1 3"));
//        // String code = ResourceUtil.readStr("testCode/simpleComputeArgs/Main.java",
//        // StandardCharsets.UTF_8);
//
//        String code = "public class Main {\n" +
//                "    public static void main(String[] args) {\n" +
//                "        // 输出 Hello World 字符串\n" +
//                "        System.out.println(\"Hello World\");\n" +
//                "    }\n" +
//                "}";
//        // String code = ResourceUtil.readStr("testCode/unsafeCode/RunFileError.java",
//        // StandardCharsets.UTF_8);
//        // String code = ResourceUtil.readStr("testCode/simpleCompute/Main.java",
//        // StandardCharsets.UTF_8);
//        executeCodeRequest.setCode(code);
//        executeCodeRequest.setLanguage("java");
//        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
//        System.out.println(executeCodeResponse);
//    }

    /**
     * 3、创建容器，把文件复制到容器内
     *
     * @param userCodeFile
     * @param inputList
     * @return
     */
    @Override
    public List<ExecuteMessage> runFile(File userCodeFile, List<String> inputList) {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        // 获取默认的 Docker Client
        // 获取默认的 Docker Client
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://101.42.172.99:2375")
                .withDockerTlsVerify(false)
                .build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        PingCmd pingCmd = dockerClient.pingCmd();
        pingCmd.exec();
        // 拉取镜像
        String image = "openjdk:8-alpine";
        if (FIRST_INIT) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
        }

        System.out.println("下载完成");

        // 创建容器

        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        HostConfig hostConfig = new HostConfig();
        hostConfig.withMemory(100 * 1000 * 1000L);
        hostConfig.withMemorySwap(0L);
        hostConfig.withCpuCount(1L);
        // hostConfig.withSecurityOpts(Arrays.asList("seccomp=安全管理配置字符串"));
        // todo 修改成服务器
        CreateContainerResponse createContainerResponse = containerCmd
                .withHostConfig(hostConfig)
                .withNetworkDisabled(true)
                .withReadonlyRootfs(false)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withTty(true)
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();

        dockerClient.copyArchiveToContainerCmd(containerId)
                .withRemotePath("/root").withHostResource(userCodeParentPath).exec();

        // 启动容器
        dockerClient.startContainerCmd(containerId).exec();

        // docker exec keen_blackwell java -cp /app Main 1 3
        // 执行命令并获取结果
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            StopWatch stopWatch = new StopWatch();
            String[] inputArgsArray = inputArgs.split(" ");
            String[] cmdArray = ArrayUtil.append(
                    new String[] { "java", "-cp", "/root/" + userCodeFile.getParentFile().getName(), "Main" },
                    inputArgsArray);
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .exec();
            System.out.println("创建执行命令：" + execCreateCmdResponse);

            ExecuteMessage executeMessage = new ExecuteMessage();
            final String[] message = { null };
            final String[] errorMessage = { null };
            long time = 0L;
            // 判断是否超时
            final boolean[] timeout = { true };
            String execId = execCreateCmdResponse.getId();
            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
                @Override
                public void onComplete() {
                    // 如果执行完成，则表示没超时
                    timeout[0] = false;
                    super.onComplete();
                }

                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    if (StreamType.STDERR.equals(streamType)) {
                        errorMessage[0] = new String(frame.getPayload());
                        System.out.println("输出错误结果：" + errorMessage[0]);
                    } else {
                        message[0] = new String(frame.getPayload());
                        System.out.println("输出结果：" + message[0]);
                    }
                    super.onNext(frame);
                }
            };

            final long[] maxMemory = { 0L };

            log.info("start to get container usage {}", containerId);
            InvocationBuilder.AsyncResultCallback<Statistics> callback = new InvocationBuilder.AsyncResultCallback<>();
            dockerClient.statsCmd(containerId).exec(callback);
            Statistics stats;
            try {
                stats = callback.awaitResult();
                maxMemory[0] = stats.getMemoryStats().getUsage() >= maxMemory[0] ? stats.getMemoryStats().getUsage()
                        : maxMemory[0];
                callback.close();
                log.info("container {} maxMemory {}", containerId, maxMemory[0]);
            } catch (RuntimeException | IOException e) {
                log.error("getStatisticsError {}", ExceptionUtils.getStackTrace(e));
            }
            try {
                // todo
                stopWatch.start();
                dockerClient.execStartCmd(execId)
                        .exec(execStartResultCallback)
                        .awaitCompletion(TIME_OUT, TimeUnit.SECONDS);
                stopWatch.stop();
                time = stopWatch.getLastTaskTimeMillis();
            } catch (InterruptedException e) {
                System.out.println("程序执行异常");
                throw new RuntimeException(e);
            }
            executeMessage.setMessage(message[0]);
            executeMessage.setErrorMessage(errorMessage[0]);
            executeMessage.setTime(time);
            executeMessage.setMemory(maxMemory[0]);
            executeMessageList.add(executeMessage);

        }
        try {
            dockerClient.stopContainerCmd(containerId).exec();
        } catch (Exception e) {
            System.err.println("停止容器失败: " + containerId);
        }

        try {
            dockerClient.removeContainerCmd(containerId).withForce(true).exec();
            System.out.println("容器已删除: " + containerId);
        } catch (Exception e) {
            System.err.println("删除容器失败: " + containerId);
        }

        return executeMessageList;
    }
}
