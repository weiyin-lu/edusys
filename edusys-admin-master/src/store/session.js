const session = {}

session.set = (key, value) => {
    if (typeof value == "object") {
        value = JSON.stringify(value)
    }
    return window.sessionStorage.setItem(key, value)
}
session.get = key => {
    const data = window.sessionStorage.getItem(key)
    if (data) {
        let value = data
        if (data.indexOf("{") == 0 || data.indexOf("[") == 0) {
            value = JSON.parse(data)
        }
        return value
    } else {
        return false
    }
}
session.clear = key => {
    window.sessionStorage.removeItem(key);
}

export default session;