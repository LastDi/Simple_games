package carsharing.company.dao;

import carsharing.company.controller.Connect;
import carsharing.company.model.Company;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao{
    private Connect connect;
    private List<Company> companies;

    public CompanyDaoImpl(Connect connect) {
        this.companies = new ArrayList<>();
        this.connect = connect;
    }

    @Override
    public List<Company> getCompanies() {
        companies = connect.getAllCompanies();
        return companies;
    }

    @Override
    public void insertCompany(Company company) {
        connect.insertCompany(company);
    }
}
