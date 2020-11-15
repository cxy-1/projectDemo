export const handleTabSize = (result, tabCellWidth) => {
    let arrColumn = [],
        bytes = {};
    result.data.map(val => {
        Object.keys(val).forEach(key => {
            let length = 0;
            for (let i = 0; i < String(val[key]).length; i++) {
                length += String(val[key]).charCodeAt(i) > 255 ? 2 : 1;
            }
            bytes[key] = Math.max(bytes[key] || 0, length);
        });
    });
    let max = 0,
        sum = 0;
    Object.keys(bytes).forEach(key => {
        sum += bytes[key];
        max = Math.max(bytes[key], max);
    });
    Object.keys(result.column).forEach(key => {
        let length, minWidth = 0;
        if (bytes[key] === max) {
            length = tabCellWidth - (sum - max) * 12;
        } else {
            length = bytes[key] * 12;
        }
        if (result.column[key] === "单位") {
          minWidth = 200;
        }
        arrColumn.push({
            prop: key,
            label: result.column[key],
            length,
            minWidth
        });
    });
    return arrColumn;
};