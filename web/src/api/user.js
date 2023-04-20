import request from "@/request"

export const loginApi = (UserInfo) => request.post('/user/login', UserInfo);

export const addUserApi = (UserInfo) => request.post('/user/addUser',UserInfo);

export const listUserApi = () => request.get('/user/listUser');

export const deleteUserApi = (UserInfo) => request.get('/user/deleteUser/'+ UserInfo);

export const updateUserApi = (UserInfo) => request.post('/user/updateUser', UserInfo);
