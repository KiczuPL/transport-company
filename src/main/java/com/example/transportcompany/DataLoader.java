package com.example.transportcompany;

import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.services.CompanyService;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.example.transportcompany.security.RoleEnum.ROLE_ADMIN;
import static com.example.transportcompany.security.RoleEnum.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserService userService;
    private final CompanyService companyService;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @PostConstruct
    public void loadData() {
        roleRepository.saveAll(List.of(
                new Role(null, ROLE_ADMIN.toString()),
                new Role(null, ROLE_USER.toString())

        ));

        loadCompanies();
        loadUsers();

        Company company  = new Company(null,"asd","asd","asd");
        companyService.saveCompany(company);


        userService.saveUser(new User(null, "Kiczu", "1234","Kiczu@@@asd","Michał", "Tomczyk", company, new ArrayList<>()));
        userService.saveUser(new User(null,  "user", "pass","user@","Adam","Nowak", company, new ArrayList<>()));


        userService.addRoleToUser("Kiczu",ROLE_ADMIN.toString());
        userService.addRoleToUser("Kiczu", ROLE_USER.toString());
        userService.addRoleToUser("user",ROLE_USER.toString());


    }

    public void loadCompanies(){
        companyService.saveCompany(new Company(null, "Ernser-Crona", "0a629bdb-81f7-4d09-8b91-bcd92c8bf471", "9 Hayes Park"));
        companyService.saveCompany(new Company(null, "Koch-Gulgowski", "990c7f88-bd30-4e71-9bd2-effaf6849e1d", "7 Blackbird Crossing"));
        companyService.saveCompany(new Company(null, "Sipes-Ziemann", "f45f2634-e31a-43fa-8de6-eb8161b6d351", "5 Larry Trail"));
        companyService.saveCompany(new Company(null, "Donnelly, Murazik and Bruen", "38ed036b-4091-49f9-9847-8879250dff97", "26357 Fulton Point"));
        companyService.saveCompany(new Company(null, "Huel, Pacocha and Conroy", "6e1d0577-b2b7-4591-befa-aab0759cb8fb", "27 Daystar Lane"));
        companyService.saveCompany(new Company(null, "Olson, Osinski and Considine", "107b630b-4040-41b9-8274-e8784c98e339", "31 Nelson Center"));
        companyService.saveCompany(new Company(null, "McDermott, Von and Jacobson", "081c723c-fc4a-483b-bc7d-1ba2b8139a74", "225 Rutledge Terrace"));
        companyService.saveCompany(new Company(null, "Bashirian-Reynolds", "0928ac96-56e2-492c-a318-1624cba97c8b", "60960 Grover Plaza"));
        companyService.saveCompany(new Company(null, "Fahey-Casper", "8f4b7688-3c4a-4b8e-93b8-a72330be3dc7", "87 Fairview Alley"));
        companyService.saveCompany(new Company(null, "Casper Inc", "939ce2cc-45e2-4050-b344-13ef51ff9ef4", "4 Steensland Way"));
        companyService.saveCompany(new Company(null, "Skiles-Hammes", "7e6a9629-49e2-4ebf-af38-dcabeed4badf", "4 Forest Dale Park"));
        companyService.saveCompany(new Company(null, "Ortiz-Thompson", "8e0c449f-a4de-45dd-9e1d-426736f45833", "946 Bluejay Circle"));
        companyService.saveCompany(new Company(null, "Murazik, Schimmel and Corwin", "431b06b6-0ba6-4457-bacd-f6491d0e32a4", "6114 Oneill Street"));
        companyService.saveCompany(new Company(null, "Carroll-Brakus", "ca44811e-ccdf-40b1-92a3-24b90f2557d5", "9 Havey Road"));
        companyService.saveCompany(new Company(null, "Krajcik, Johns and Kuhn", "f0cdeecf-9e3b-42d7-93be-9a1782c953c8", "55599 Shopko Center"));
        companyService.saveCompany(new Company(null, "Heathcote, Von and Cassin", "ab0f130a-54ec-466c-9a4d-10484bb8436f", "9182 Eliot Junction"));
        companyService.saveCompany(new Company(null, "Towne-Mante", "0951d7e4-ff48-4f6b-8172-5f651da02df7", "7 Nancy Point"));
        companyService.saveCompany(new Company(null, "Schmitt Group", "8bb3d7e9-700c-4e30-92be-fc15f6eef8f2", "4117 Fuller Junction"));
        companyService.saveCompany(new Company(null, "Dibbert-Franecki", "f70f5c87-c230-4494-a8af-516e5cccc1be", "1 Portage Point"));
        companyService.saveCompany(new Company(null, "Schamberger-Hudson", "a0824bdb-71c8-4000-9dd9-4ac26e9cbf64", "9 Nelson Pass"));
    }

    public void loadUsers(){
        userService.saveUser(new User(null, "lgoathrop0", "nDPvJWL", "lgoathrop0@jugem.jp", "Lilyan", "Goathrop", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "nfehners1", "XAnZ1jwXAA", "nfehners1@wired.com", "Nial", "Fehners", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "adonkersley2", "1JmhX0aGnf", "adonkersley2@wikipedia.org", "Amalita", "Donkersley", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "mmowles3", "YPtgmFPO7zoi", "mmowles3@vimeo.com", "Mervin", "Mowles", companyRepository.getReferenceById(6L), new ArrayList<>()));
        userService.saveUser(new User(null, "mbrocklebank4", "lGMo4epTp1", "mbrocklebank4@twitter.com", "Maje", "Brocklebank", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "jfernan5", "A0rI59", "jfernan5@youtube.com", "Jarrod", "Fernan", companyRepository.getReferenceById(8L), new ArrayList<>()));
        userService.saveUser(new User(null, "bivanshintsev6", "hdCXU1c6q", "bivanshintsev6@paypal.com", "Brooke", "Ivanshintsev", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "kcavalier7", "mvoNa4eBDe4", "kcavalier7@intel.com", "Karim", "Cavalier", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "tscrewton8", "YCKdzpC5nbZK", "tscrewton8@netscape.com", "Therine", "Screwton", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "wtennock9", "fsTpXoGHY7tG", "wtennock9@ezinearticles.com", "Wainwright", "Tennock", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "mcochrana", "T9jzrClhzXx", "mcochrana@globo.com", "Matthaeus", "Cochran", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "lcookesb", "bsWo7V7b", "lcookesb@ocn.ne.jp", "Lay", "Cookes", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "rgreastyc", "x3PPt88NglL", "rgreastyc@thetimes.co.uk", "Roma", "Greasty", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "candreottid", "QQjaVW", "candreottid@weather.com", "Caroline", "Andreotti", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "gcrowcombee", "HVMwnVSt", "gcrowcombee@wisc.edu", "Gaby", "Crowcombe", companyRepository.getReferenceById(10L), new ArrayList<>()));
        userService.saveUser(new User(null, "hbackef", "wbD2tI", "hbackef@php.net", "Harland", "Backe", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "averdieg", "kO8qs5", "averdieg@paypal.com", "Aggy", "Verdie", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "ldzenisenkah", "Tvkz3C9m32z", "ldzenisenkah@networksolutions.com", "Leandra", "Dzenisenka", companyRepository.getReferenceById(14L), new ArrayList<>()));
        userService.saveUser(new User(null, "mfettesi", "luelgK", "mfettesi@eventbrite.com", "Morton", "Fettes", companyRepository.getReferenceById(13L), new ArrayList<>()));
        userService.saveUser(new User(null, "tbatstonej", "fI3NzZPk", "tbatstonej@blogs.com", "Thorn", "Batstone", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "khellyerk", "5Tnp4I", "khellyerk@shutterfly.com", "Korey", "Hellyer", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "srentallll", "rabzblH1U8XD", "srentallll@un.org", "Stephi", "Rentalll", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "riacovuzzim", "Zqapv83zFdI", "riacovuzzim@infoseek.co.jp", "Roselle", "Iacovuzzi", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "ttitmarshn", "W4W0XKXHYw", "ttitmarshn@ed.gov", "Truda", "Titmarsh", companyRepository.getReferenceById(6L), new ArrayList<>()));
        userService.saveUser(new User(null, "cdewburyo", "VuEIIcyRuU", "cdewburyo@4shared.com", "Corey", "Dewbury", companyRepository.getReferenceById(3L), new ArrayList<>()));
        userService.saveUser(new User(null, "cmarmonp", "SSYh78WrKL", "cmarmonp@feedburner.com", "Conroy", "Marmon", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "tmeysq", "zt40nfA0", "tmeysq@washington.edu", "Teriann", "Meys", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "aduddellr", "zHO3Ly", "aduddellr@clickbank.net", "Annissa", "Duddell", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "bgantleys", "E5UH0fQ0fK", "bgantleys@marriott.com", "Bard", "Gantley", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "hewbanchet", "bCNi8XNQaVA", "hewbanchet@java.com", "Hewitt", "Ewbanche", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "gschollingu", "TdcMsX6U", "gschollingu@answers.com", "Gayelord", "Scholling", companyRepository.getReferenceById(12L), new ArrayList<>()));
        userService.saveUser(new User(null, "hritchleyv", "fjMEPtH3BeTe", "hritchleyv@elegantthemes.com", "Haily", "Ritchley", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "ftorbeckw", "yyVpedBUu0m", "ftorbeckw@example.com", "Felicio", "Torbeck", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "fgollinx", "KP6W8Tu6wLPC", "fgollinx@feedburner.com", "Fletch", "Gollin", companyRepository.getReferenceById(6L), new ArrayList<>()));
        userService.saveUser(new User(null, "sandreuttiy", "NOcx1t", "sandreuttiy@wp.com", "Sarajane", "Andreutti", companyRepository.getReferenceById(8L), new ArrayList<>()));
        userService.saveUser(new User(null, "lfairhurstz", "yzwgQJEYz", "lfairhurstz@rakuten.co.jp", "Legra", "Fairhurst", companyRepository.getReferenceById(8L), new ArrayList<>()));
        userService.saveUser(new User(null, "gparadine10", "Wx2XA9tov", "gparadine10@reddit.com", "Gavin", "Paradine", companyRepository.getReferenceById(19L), new ArrayList<>()));
        userService.saveUser(new User(null, "ksuggate11", "a0AaYsRdVSjw", "ksuggate11@is.gd", "Karlen", "Suggate", companyRepository.getReferenceById(9L), new ArrayList<>()));
        userService.saveUser(new User(null, "cborrott12", "cxK9Ayvk4", "cborrott12@state.gov", "Cathleen", "Borrott", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "okhoter13", "H0htiEdFH", "okhoter13@seesaa.net", "Odelinda", "Khoter", companyRepository.getReferenceById(18L), new ArrayList<>()));
        userService.saveUser(new User(null, "dboylin14", "HLZcHqfgC7ay", "dboylin14@accuweather.com", "Der", "Boylin", companyRepository.getReferenceById(8L), new ArrayList<>()));
        userService.saveUser(new User(null, "bbruckent15", "vtOKylR1W", "bbruckent15@omniture.com", "Bordy", "Bruckent", companyRepository.getReferenceById(7L), new ArrayList<>()));
        userService.saveUser(new User(null, "elankford16", "l6EF8PzDWkoM", "elankford16@cnbc.com", "Eddie", "Lankford", companyRepository.getReferenceById(6L), new ArrayList<>()));
        userService.saveUser(new User(null, "kmcquillin17", "r3TmKxzwfaOi", "kmcquillin17@homestead.com", "Kalindi", "McQuillin", companyRepository.getReferenceById(6L), new ArrayList<>()));
        userService.saveUser(new User(null, "lpedrollo18", "UqRKvVaBu", "lpedrollo18@shareasale.com", "Leigh", "Pedrollo", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "wmatyushonok19", "3MfPIv", "wmatyushonok19@opensource.org", "Waring", "Matyushonok", companyRepository.getReferenceById(17L), new ArrayList<>()));
        userService.saveUser(new User(null, "lbrastead1a", "5RnpY49", "lbrastead1a@alibaba.com", "Lorrin", "Brastead", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "kdestouche1b", "ViaEtrdjHo", "kdestouche1b@miitbeian.gov.cn", "Kalil", "Destouche", companyRepository.getReferenceById(5L), new ArrayList<>()));
        userService.saveUser(new User(null, "twaddie1c", "88cWdokz", "twaddie1c@i2i.jp", "Tod", "Waddie", companyRepository.getReferenceById(4L), new ArrayList<>()));
        userService.saveUser(new User(null, "bpedwell1d", "TGwXODc3HE", "bpedwell1d@pinterest.com", "Benjie", "Pedwell", companyRepository.getReferenceById(9L), new ArrayList<>()));
    }
}
