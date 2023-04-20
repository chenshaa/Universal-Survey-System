import request from "@/request"

export const listPaperApi = (qid) => request.get('/paper/listPaper/' + qid);

export const updatePaperApi = (qid, PaperInfo) => request.post('/paper/updatePaper/' + qid, PaperInfo);