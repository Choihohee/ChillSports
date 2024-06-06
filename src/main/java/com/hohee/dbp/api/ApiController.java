package com.hohee.dbp.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hohee.dbp.company.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiService apiService;

    @GetMapping("/api")
    public String callApi() throws IOException {
        StringBuilder result = new StringBuilder();

        String urlStr = "http://opendata.anyang.go.kr:8082/openApi/3830000/getAnyangPafbizSttus?numOfRows=100&pageNo=1";

        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = null;
        BufferedReader br = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000); // 10초로 연결 타임아웃 설정
            urlConnection.setReadTimeout(10000); // 10초로 읽기 타임아웃 설정

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine).append("\n\r");
            }

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.toString());
            JsonNode itemsNode = rootNode.path("response").path("body").path("items");

            List<Company> companyList = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                Company company = new Company();

                company.setLctn(itemNode.path("lctn").asText());
                company.setTpbiz(itemNode.path("tpbiz").asText());
                company.setConm(itemNode.path("conm").asText());
                company.setTelno(itemNode.path("telno").asText());

                logger.info("Parsed company: " + company);

                companyList.add(company);
            }

            // 데이터베이스에 저장
            apiService.saveAll(companyList);

            logger.info("Successfully saved all companies.");

        } catch (IOException e) {
            logger.error("API 호출 중 오류가 발생했습니다: " + e.getMessage());
            return "API 호출 중 오류가 발생했습니다: " + e.getMessage();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return "데이터가 성공적으로 저장되었습니다.";
    }
}
