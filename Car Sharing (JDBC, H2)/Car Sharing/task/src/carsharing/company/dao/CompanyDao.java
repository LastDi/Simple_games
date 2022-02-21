package carsharing.company.dao;

import carsharing.company.model.Company;
import java.util.List;

public interface CompanyDao {
    List<Company> getCompanies();
    void insertCompany(Company company);
}
