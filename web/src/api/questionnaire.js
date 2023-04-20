import request from "@/request"

export const listQuestionnaireApi = () => request.get('/questionnaire/listQuestionnaire');

export const updateQuestionnaireApi = (QuestionnaireInfo) => request.post('/questionnaire/updateQuestionnaire', QuestionnaireInfo);

export const getQuestionnaireDetailApi = (id) => request.get('/questionnaire/getQuestionnaireDetail/' + id);

export const setQuestionnaireTimeApi = (TimeParams) => request.post('/questionnaire/setQuestionnaireTime', TimeParams);

export const uploadAnswerApi = (info) => request.post('/questionnaire/uploadAnswer', info);

export const deleteQuestionnaireApi = (id) => request.get('/questionnaire/deleteQuestionnaire/' + id);

export const getQuestionnaireReportApi = (id) => request.get('/questionnaire/getQuestionnaireReport/' + id);

export const setQuestionnaireAnonymousApi = (id, state) => request.get('/questionnaire/setQuestionnaireAnonymous/' + id + '/' + state);

export const getStatisticsDataApi = () => request.get('/questionnaire/getStatisticsData');