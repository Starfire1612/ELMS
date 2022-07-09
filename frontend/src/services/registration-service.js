import http from "../utils/http-common.js.js";
class RegistrationService {
  getAll() {
    return http.get("/students");
  }
  //   get(id) {
  //     return http.get(`/tutorials/${id}`);
  //   }
  //   create(data) {
  //     return http.post("/tutorials", data);
  //   }
  create(user, type) {
    return http.post(`/register-user/${user}/type/${type}`);
  }
  //   delete(id) {
  //     return http.delete(`/tutorials/${id}`);
  //   }
  //   deleteAll() {
  //     return http.delete(`/tutorials`);
  //   }
  //   findByTitle(title) {
  //     return http.get(`/tutorials?title=${title}`);
  //   }
}
export default new RegistrationService();
