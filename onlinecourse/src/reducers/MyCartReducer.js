const MyCartReducer = (currentState, action) => {
    if (action.type === 'update')
        return currentState + action.payload;
    if (action.type === 'reset')
        return 0;
    return currentState;
}

export default MyCartReducer;