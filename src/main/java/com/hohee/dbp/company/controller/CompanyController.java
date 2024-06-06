package com.hohee.dbp.company.controller;

import com.hohee.dbp.api.ApiService;
import com.hohee.dbp.company.entity.Company;
import com.hohee.dbp.company.service.CompanyService;
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
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ApiService apiService;

    //첫 페이지
    @GetMapping
    public String viewFirstPage(Model model){
        return "first_page";
    }

    //관리자페이지 리스트조회
    @GetMapping("/companyList/admin")
    public String companyList(Model model){
        List<Company> companyList = apiService.getAllCompanies();
        model.addAttribute("companyList", companyList);
        return "company_list_admin";
    }

    //관리자페이지 상세정보 조회
    @GetMapping("/detail/admin/{companyId}") // URL에 companyId 포함
    public String detailCompany(Model model, @PathVariable("companyId") Long companyId){
        Company company = companyService.findById(companyId);
        model.addAttribute("company", company);
        return "company_detail_admin";
    }

    //검색기능
    @GetMapping("/search/admin")
    public String searchCompany(@RequestParam("keyword") String keyword, Model model) {
        List<Company> searchResultByConm = apiService.searchByConm(keyword);
        List<Company> searchResultByTpbiz = apiService.searchByTpbiz(keyword);

        // 두 검색 결과를 합칩니다.
        Set<Company> combinedSearchResult = new HashSet<>();
        combinedSearchResult.addAll(searchResultByConm);
        combinedSearchResult.addAll(searchResultByTpbiz);

        model.addAttribute("searchResult", new ArrayList<>(combinedSearchResult));
        return "search_result_admin";
    }

    // 업체 정보 수정페이지 이동
    @GetMapping("/update/{companyId}")
    public String viewupdateCompanyInfo(Model model, @PathVariable Long companyId) {
        Company company = companyService.findById(companyId);
        model.addAttribute("company", company);
        return "update_company_info";
    }

    // 업체 정보 수정
    @PostMapping("/update/{companyId}")
    public String updateCompanyInfo(@PathVariable Long companyId, @ModelAttribute Company company) {
        company.setCompanyId(companyId);
        companyService.save(company);
        return "redirect:/chillSports/detail/admin/" + companyId;
    }
}
