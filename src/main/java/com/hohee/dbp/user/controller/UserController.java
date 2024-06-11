package com.hohee.dbp.user.controller;

import com.hohee.dbp.api.ApiService;
import com.hohee.dbp.api.entity.Company;
import com.hohee.dbp.admin.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/chillSports")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AdminService companyService;

    @Autowired
    private ApiService apiService;

    //업체 리스트 조회
    @GetMapping("/companyList")
    public String companyList(Model model){
        List<Company> companyList = apiService.getAllCompanies();
        model.addAttribute("companyList", companyList);
        return "company_list";
    }

    //상세정보 조회
    @GetMapping("/detail/{companyId}") // URL에 companyId 포함
    public String detailCompany(Model model, @PathVariable("companyId") Long companyId){
        Company company = companyService.findById(companyId);
        model.addAttribute("company", company);
        return "company_detail";
    }

    //검색기능
    @GetMapping("/search")
    public String searchCompany(@RequestParam("keyword") String keyword, Model model) {
        List<Company> searchResultByConm = apiService.searchByConm(keyword);
        List<Company> searchResultByTpbiz = apiService.searchByTpbiz(keyword);

        Set<Company> combinedSearchResult = new HashSet<>();
        combinedSearchResult.addAll(searchResultByConm);
        combinedSearchResult.addAll(searchResultByTpbiz);

        model.addAttribute("searchResult", new ArrayList<>(combinedSearchResult));
        return "search_result";
    }
}
