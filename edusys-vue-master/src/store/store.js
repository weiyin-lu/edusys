import {createStore} from 'vuex'

const store = createStore({
    state() {
        return {
            step: 33,
            studentInfo: {
                name: null,
                id: null
            },
            parseInfo: {
                name: null,
                id: null,
                height: null,
                weight: null,
                shoeSize: null,
                parseShoes: null,
                parseShirt: null,
                parseCloth: null
            }
        }
    },
    getters: {
        getStep(state) {
            return state.step
        },
        getStudentInfo(state) {
            return state.studentInfo
        },
        getParseInfo(state) {
            return state.parseInfo
        }
    },
    mutations: {
        commitStep(state, stepNum) {
            state.step = stepNum
        },
        commitStudentInfo(state, value) {
            state.studentInfo.id = value.id;
            state.studentInfo.name = value.name;
        },
        commitParseInfo(state, value) {
            state.parseInfo.id = value.id;
            state.parseInfo.name = value.name;
            state.parseInfo.height = value.height
            state.parseInfo.weight = value.weight
            state.parseInfo.shoeSize = value.shoeSize
            state.parseInfo.parseShoes = value.parseShoes
            state.parseInfo.parseShirt = value.parseShirt
            state.parseInfo.parseCloth = value.parseCloth
        }
    },
    actions: {}
})

export default store