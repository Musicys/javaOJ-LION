export const copyMatchingProperties = (target: any, source: any) => {
    for (const key in source) {
        if (key in target) {
            target[key] = source[key];
        }
    }
    return target;
}
