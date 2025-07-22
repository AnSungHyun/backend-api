//package com.elysium.backend;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//
//import org.springframework.restdocs.payload.JsonFieldType;
////import org.junit.runner.RunWith;
////import org.springframework.test.context.junit4.SpringRunner;
////import org.springframework.test.context.junit.jupiter.SpringExtension;
////import org.springframework.restdocs.RestDocumentationExtension;
////import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.elysium.backend.utils.DocumentUtils.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
////@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//public class UserControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("Login Test")
//    public void userLoginTest() throws Exception {
//
//        //given
//        String requestFields = "{\"username\": \"ASH\", \"password\": \"aa\"}";
//
//        //when
//        ResultActions result = this.mockMvc.perform(post("/api/user/login")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(requestFields)
//        );
//
//        //then
//        result.andExpect(status().isOk()) // expected 200 OK status
//                .andDo(document("user-login", getDocumentRequest(), getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("username").type(JsonFieldType.STRING).description("아이디").attributes(getExampleAttribute("예) ASH")),
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").attributes(getExampleAttribute("예) Am123456"))
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드").attributes(getExampleAttribute("예) 0")),
//                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("토큰").attributes(getExampleAttribute("예) Q2RQWF3W5H7ZWE")),
//                                fieldWithPath("data.id").type(JsonFieldType.STRING).description("아이디").attributes(getExampleAttribute("예) ASH")),
//                                fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저명").attributes(getExampleAttribute("예) 안성현")),
//                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일").attributes(getExampleAttribute("예) shan@cware.co.kr")),
//                                fieldWithPath("data.roles").type(JsonFieldType.ARRAY).description("권한").attributes(getExampleAttribute("예) ROLE_ADMIN")),
//                                fieldWithPath("data.tokenType").type(JsonFieldType.STRING).description("토큰타입").attributes(getExampleAttribute("예) Bearer"))
//                        )
//                ));
//
//    }
//
//    public String getToken() throws Exception {
//
//        //given
//        String requestFields = "{\"username\": \"ASH\", \"password\": \"aa\"}";
//
//        //when
//        ResultActions result = this.mockMvc.perform(post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestFields)
//        );
//
//        String responseBody = result.andReturn().getResponse().getContentAsString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = objectMapper.readTree(responseBody);
//        String jwtToken = rootNode.path("data").path("token").asText();
//        return jwtToken;
//
//    }
//
//    @Test
//    @DisplayName("Current User Info Test")
//    public void getLoginUserInfo() throws Exception {
//
//        //given
//        String token = getToken();
//
//        //when
//        ResultActions result = this.mockMvc.perform(get("/api/user/info")
//            .contentType(MediaType.APPLICATION_JSON)
//            .header("Authorization", "Bearer " + token)
//        );
//
//        //then
//        result.andExpect(status().isOk()) // expected 200 OK status
//              .andDo(document("user-info", getDocumentRequest(), getDocumentResponse(),
//                   responseFields(
//                       fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드").attributes(getExampleAttribute("예) 0")),
//                       fieldWithPath("data.userId").type(JsonFieldType.STRING).description("아이디").attributes(getExampleAttribute("예) ASH")),
//                       fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저명").attributes(getExampleAttribute("예) 안성현")),
//                       fieldWithPath("data.telNo").type(JsonFieldType.STRING).description("전화번호").attributes(getExampleAttribute("예) 01044803543")),
//                       fieldWithPath("data.lang").type(JsonFieldType.STRING).description("언어").attributes(getExampleAttribute("예) ko")),
//                       fieldWithPath("data.role").type(JsonFieldType.STRING).description("권한").attributes(getExampleAttribute("예) admin")),
//                       fieldWithPath("data.roleId").type(JsonFieldType.STRING).description("권한아이디").attributes(getExampleAttribute("예) 1")),
//                       fieldWithPath("data.permissions").type(JsonFieldType.ARRAY).description("권한").attributes(getExampleAttribute("예) *.*.*")),
//                       fieldWithPath("data.authority").type(JsonFieldType.STRING).description("권한").attributes(getExampleAttribute("예) admin"))
//                   )
//              ));
//    }
//
//    @Test
//    @DisplayName("Change Password Test")
//    @Transactional
//    public void changePassword() throws Exception {
//
//        //given
//        String token = getToken();
//        String requestFields = "{\"currentPassword\": \"aa\", \"newPassword\": \"aa\"}";
//
//        //when
//        ResultActions result = this.mockMvc.perform(post("/api/user/change-password")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + token)
//                .content(requestFields)
//        );
//        //then
//        result.andExpect(status().isOk()) // expected 200 OK status
//                .andDo(document("change-password", getDocumentRequest(), getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("currentPassword").type(JsonFieldType.STRING).description("현재비밀번호"),
//                                fieldWithPath("newPassword").type(JsonFieldType.STRING).description("신규비밀번호").attributes(getExampleAttribute("길이 : 8~10" + newLine() + "대문자 + 소문자 + 숫자 조합"))
//                        ),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("결과코드").attributes(getExampleAttribute("예) 0")),
//                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("토큰").attributes(getExampleAttribute("예) Q2RQWF3W5H7ZWE")),
//                                fieldWithPath("data.id").type(JsonFieldType.STRING).description("아이디").attributes(getExampleAttribute("예) ASH")),
//                                fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저명").attributes(getExampleAttribute("예) 안성현")),
//                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일").attributes(getExampleAttribute("예) shan@cware.co.kr")),
//                                fieldWithPath("data.roles").type(JsonFieldType.ARRAY).description("권한").attributes(getExampleAttribute("예) ROLE_ADMIN")),
//                                fieldWithPath("data.tokenType").type(JsonFieldType.STRING).description("토큰타입").attributes(getExampleAttribute("예) Bearer"))
//                        )
//                ));
//    }
//
//}
