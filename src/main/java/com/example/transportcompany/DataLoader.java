package com.example.transportcompany;

import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Role;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.forms.CreateUserForm;
import com.example.transportcompany.model.forms.CreateVehicleForm;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.RoleRepository;
import com.example.transportcompany.services.CompanyService;
import com.example.transportcompany.services.OrderService;
import com.example.transportcompany.services.UserService;
import com.example.transportcompany.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static com.example.transportcompany.security.RoleEnum.ROLE_ADMIN;
import static com.example.transportcompany.security.RoleEnum.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserService userService;
    private final CompanyService companyService;
    private final OrderService orderService;
    private final VehicleService vehicleService;
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
        loadOrders();
        loadVehicles();

        Company company = new Company(null, "asd", "asd222", "asd");
        companyService.saveCompany(company);


        userService.saveUser(new CreateUserForm("Kiczu", "Kiczu@@@asd", "Michał", "Tomczyk", 13L));

        userService.saveUser(new CreateUserForm("user", "user@", "Adam", "Nowak", 5L));
        userService.saveUser(new CreateUserForm("user2", "user@2", "Adam", "Nowak", 10L));
        userService.changePassword("Kiczu", "1234");
        userService.changePassword("user", "1234");
        userService.changePassword("user2", "pass");


        userService.addRoleToUser("Kiczu", ROLE_ADMIN.toString());
        //userService.addRoleToUser("Kiczu", ROLE_USER.toString());
        userService.addRoleToUser("user", ROLE_USER.toString());
        userService.addRoleToUser("user2", ROLE_USER.toString());


    }

    private void loadVehicles() {
        vehicleService.saveVehicle(new CreateVehicleForm("A1","WAW12345",VehicleType.SEMI_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("A2","WOT12300",VehicleType.DELIVERY_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("A5","WPA12399",VehicleType.TANK_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("A3","XYZ12377",VehicleType.SEMI_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("B1","WAW11111",VehicleType.SEMI_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("B2","WOT22222",VehicleType.DELIVERY_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("B5","WPA33333",VehicleType.TANK_TRUCK));
        vehicleService.saveVehicle(new CreateVehicleForm("B3","XYZ44444",VehicleType.SEMI_TRUCK));
    }

    public void loadOrders() {
        orderService.saveOrder(new CreateOrderForm("95 Anniversary Point", "05 Debra Drive", 4L, LocalDate.parse("2022-06-01"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("9 Park Meadow Point", "3 Ridgeview Terrace", 13L, LocalDate.parse("2022-10-21"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("0 Mosinee Pass", "56 Beilfuss Alley", 15L, LocalDate.parse("2022-02-13"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("66 Brickson Park Junction", "90 Shopko Hill", 12L, LocalDate.parse("2022-01-13"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("3628 Florence Lane", "99595 Thackeray Terrace", 5L, LocalDate.parse("2022-10-18"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("203 Lien Drive", "042 Warrior Junction", 10L, LocalDate.parse("2022-08-08"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("1123 Fair Oaks Park", "29280 Continental Circle", 4L, LocalDate.parse("2022-08-18"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("6069 Stone Corner Plaza", "3 Dwight Trail", 17L, LocalDate.parse("2022-01-02"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("1 Birchwood Crossing", "6 Sutherland Junction", 4L, LocalDate.parse("2022-02-01"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("27 Maple Lane", "4210 Scott Crossing", 13L, LocalDate.parse("2022-02-07"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("1 Walton Circle", "34 Forest Avenue", 13L, LocalDate.parse("2021-11-18"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("6 Thackeray Terrace", "9 Arapahoe Junction", 12L, LocalDate.parse("2022-06-14"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("10 Moose Lane", "0 Lunder Alley", 12L, LocalDate.parse("2021-12-09"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("55 Prairie Rose Drive", "0 Gina Hill", 13L, LocalDate.parse("2021-12-12"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("758 Kipling Avenue", "65243 Ridgeview Way", 4L, LocalDate.parse("2022-02-06"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("49131 Oak Drive", "81577 Fairview Lane", 13L, LocalDate.parse("2022-04-10"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("766 Dexter Plaza", "80 Golf Court", 5L, LocalDate.parse("2022-06-26"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("818 Derek Point", "171 Boyd Hill", 21L, LocalDate.parse("2021-12-02"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("6 La Follette Junction", "88653 Oakridge Plaza", 5L, LocalDate.parse("2022-05-18"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("303 Waxwing Court", "69895 Oneill Plaza", 10L, LocalDate.parse("2022-01-14"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("1 Prairie Rose Plaza", "488 Clarendon Parkway", 5L, LocalDate.parse("2021-12-27"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("08638 Grover Lane", "60254 Pond Hill", 6L, LocalDate.parse("2022-03-22"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("888 Farmco Junction", "5216 Laurel Plaza", 12L, LocalDate.parse("2022-02-01"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("07750 Gale Court", "3 Sunfield Park", 7L, LocalDate.parse("2022-01-18"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("8464 Macpherson Avenue", "61138 Hoffman Avenue", 8L, LocalDate.parse("2022-10-24"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("3 Laurel Street", "14707 Cardinal Terrace", 9L, LocalDate.parse("2022-09-29"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("161 Hanson Junction", "9704 Thierer Point", 5L, LocalDate.parse("2022-02-06"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("7 Fairview Park", "25585 Oneill Circle", 13L, LocalDate.parse("2022-05-30"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("2 Towne Hill", "7 Holmberg Parkway", 12L, LocalDate.parse("2022-06-26"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("97647 Namekagon Hill", "331 Swallow Way", 14L, LocalDate.parse("2022-07-26"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("94980 Morning Park", "566 Crowley Drive", 15L, LocalDate.parse("2022-10-02"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("72 Claremont Road", "7485 Mayfield Avenue", 10L, LocalDate.parse("2022-08-26"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("61246 Loftsgordon Road", "6766 Thierer Alley", 17L, LocalDate.parse("2022-02-08"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("8419 Johnson Point", "67668 Buena Vista Place", 18L, LocalDate.parse("2022-05-06"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("0 Stang Way", "3717 Portage Parkway", 5L, LocalDate.parse("2022-02-22"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("6996 Moulton Plaza", "5445 West Center", 5L, LocalDate.parse("2022-03-15"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("51 Fair Oaks Terrace", "8 Pankratz Hill", 4L, LocalDate.parse("2021-12-28"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("0644 Badeau Way", "5653 Farmco Terrace", 12L, LocalDate.parse("2022-01-24"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("3 Crownhardt Road", "5773 Hanover Way", 13L, LocalDate.parse("2022-11-01"), VehicleType.SEMI_TRUCK));
        orderService.saveOrder(new CreateOrderForm("3017 Bunker Hill Way", "87 Morningstar Street", 14L, LocalDate.parse("2022-06-07"), VehicleType.SEMI_TRUCK));
    }

    public void loadCompanies() {
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

    public void loadUsers() {
        userService.saveUser(new CreateUserForm("lgoathrop0", "lgoathrop0@jugem.jp", "Lilyan", "Goathrop", 4L));
        userService.saveUser(new CreateUserForm("nfehners1", "nfehners1@wired.com", "Nial", "Fehners", 7L));
        userService.saveUser(new CreateUserForm("adonkersley2", "adonkersley2@wikipedia.org", "Amalita", "Donkersley", 7L));
        userService.saveUser(new CreateUserForm("mmowles3", "mmowles3@vimeo.com", "Mervin", "Mowles", 6L));
        userService.saveUser(new CreateUserForm("mbrocklebank4", "mbrocklebank4@twitter.com", "Maje", "Brocklebank", 9L));
        userService.saveUser(new CreateUserForm("jfernan5", "jfernan5@youtube.com", "Jarrod", "Fernan", 8L));
        userService.saveUser(new CreateUserForm("bivanshintsev6", "bivanshintsev6@paypal.com", "Brooke", "Ivanshintsev", 7L));
        userService.saveUser(new CreateUserForm("kcavalier7", "kcavalier7@intel.com", "Karim", "Cavalier", 7L));
        userService.saveUser(new CreateUserForm("tscrewton8", "tscrewton8@netscape.com", "Therine", "Screwton", 7L));
        userService.saveUser(new CreateUserForm("wtennock9", "wtennock9@ezinearticles.com", "Wainwright", "Tennock", 9L));
        userService.saveUser(new CreateUserForm("mcochrana", "mcochrana@globo.com", "Matthaeus", "Cochran", 5L));
        userService.saveUser(new CreateUserForm("lcookesb", "lcookesb@ocn.ne.jp", "Lay", "Cookes", 5L));
        userService.saveUser(new CreateUserForm("rgreastyc", "rgreastyc@thetimes.co.uk", "Roma", "Greasty", 9L));
        userService.saveUser(new CreateUserForm("candreottid", "candreottid@weather.com", "Caroline", "Andreotti", 5L));
        userService.saveUser(new CreateUserForm("gcrowcombee", "gcrowcombee@wisc.edu", "Gaby", "Crowcombe", 10L));
        userService.saveUser(new CreateUserForm("hbackef", "hbackef@php.net", "Harland", "Backe", 9L));
        userService.saveUser(new CreateUserForm("averdieg", "averdieg@paypal.com", "Aggy", "Verdie", 4L));
        userService.saveUser(new CreateUserForm("ldzenisenkah", "ldzenisenkah@networksolutions.com", "Leandra", "Dzenisenka", 14L));
        userService.saveUser(new CreateUserForm("mfettesi", "mfettesi@eventbrite.com", "Morton", "Fettes", 13L));
        userService.saveUser(new CreateUserForm("tbatstonej", "tbatstonej@blogs.com", "Thorn", "Batstone", 5L));
        userService.saveUser(new CreateUserForm("khellyerk", "khellyerk@shutterfly.com", "Korey", "Hellyer", 9L));
        userService.saveUser(new CreateUserForm("srentallll", "srentallll@un.org", "Stephi", "Rentalll", 5L));
        userService.saveUser(new CreateUserForm("riacovuzzim", "riacovuzzim@infoseek.co.jp", "Roselle", "Iacovuzzi", 5L));
        userService.saveUser(new CreateUserForm("ttitmarshn", "ttitmarshn@ed.gov", "Truda", "Titmarsh", 6L));
        userService.saveUser(new CreateUserForm("cdewburyo", "cdewburyo@4shared.com", "Corey", "Dewbury", 3L));
        userService.saveUser(new CreateUserForm("cmarmonp", "cmarmonp@feedburner.com", "Conroy", "Marmon", 4L));
        userService.saveUser(new CreateUserForm("tmeysq", "tmeysq@washington.edu", "Teriann", "Meys", 5L));
        userService.saveUser(new CreateUserForm("aduddellr", "aduddellr@clickbank.net", "Annissa", "Duddell", 4L));
        userService.saveUser(new CreateUserForm("bgantleys", "bgantleys@marriott.com", "Bard", "Gantley", 7L));
        userService.saveUser(new CreateUserForm("hewbanchet", "hewbanchet@java.com", "Hewitt", "Ewbanche", 5L));
        userService.saveUser(new CreateUserForm("gschollingu", "gschollingu@answers.com", "Gayelord", "Scholling", 12L));
        userService.saveUser(new CreateUserForm("hritchleyv", "hritchleyv@elegantthemes.com", "Haily", "Ritchley", 7L));
        userService.saveUser(new CreateUserForm("ftorbeckw", "ftorbeckw@example.com", "Felicio", "Torbeck", 9L));
        userService.saveUser(new CreateUserForm("fgollinx", "fgollinx@feedburner.com", "Fletch", "Gollin", 6L));
        userService.saveUser(new CreateUserForm("sandreuttiy", "sandreuttiy@wp.com", "Sarajane", "Andreutti", 8L));
        userService.saveUser(new CreateUserForm("lfairhurstz", "lfairhurstz@rakuten.co.jp", "Legra", "Fairhurst", 8L));
        userService.saveUser(new CreateUserForm("gparadine10", "gparadine10@reddit.com", "Gavin", "Paradine", 19L));
        userService.saveUser(new CreateUserForm("ksuggate11", "ksuggate11@is.gd", "Karlen", "Suggate", 9L));
        userService.saveUser(new CreateUserForm("cborrott12", "cborrott12@state.gov", "Cathleen", "Borrott", 7L));
        userService.saveUser(new CreateUserForm("okhoter13", "okhoter13@seesaa.net", "Odelinda", "Khoter", 18L));
        userService.saveUser(new CreateUserForm("dboylin14", "dboylin14@accuweather.com", "Der", "Boylin", 8L));
        userService.saveUser(new CreateUserForm("bbruckent15", "bbruckent15@omniture.com", "Bordy", "Bruckent", 7L));
        userService.saveUser(new CreateUserForm("elankford16", "elankford16@cnbc.com", "Eddie", "Lankford", 6L));
        userService.saveUser(new CreateUserForm("kmcquillin17", "kmcquillin17@homestead.com", "Kalindi", "McQuillin", 6L));
        userService.saveUser(new CreateUserForm("lpedrollo18", "lpedrollo18@shareasale.com", "Leigh", "Pedrollo", 4L));
        userService.saveUser(new CreateUserForm("wmatyushonok19", "wmatyushonok19@opensource.org", "Waring", "Matyushonok", 17L));
        userService.saveUser(new CreateUserForm("lbrastead1a", "lbrastead1a@alibaba.com", "Lorrin", "Brastead", 5L));
        userService.saveUser(new CreateUserForm("kdestouche1b", "kdestouche1b@miitbeian.gov.cn", "Kalil", "Destouche", 5L));
        userService.saveUser(new CreateUserForm("twaddie1c", "twaddie1c@i2i.jp", "Tod", "Waddie", 4L));
        userService.saveUser(new CreateUserForm("bpedwell1d", "bpedwell1d@pinterest.com", "Benjie", "Pedwell", 9L));
    }
}
