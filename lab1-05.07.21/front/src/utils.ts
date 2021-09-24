const numComp = (a: object, b: object, key: string): number => a[key] - b[key]
const strComp = (a: object, b: object, key: string): number => a[key].length - b[key].length

export { numComp, strComp }