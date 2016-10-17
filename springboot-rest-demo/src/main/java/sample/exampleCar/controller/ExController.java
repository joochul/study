package sample.exampleCar.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.exampleCar.entity.Appearance;
import sample.exampleCar.entity.Car;
import sample.exampleCar.entity.Engine;
import sample.exampleCar.entity.Memo;
import sample.exampleCar.entity.Owner;
import sample.exampleCar.entity.Status;
import sample.exampleCar.entity.Tire;
import sample.exampleCar.repository.ExRepository;

@RestController
@RequestMapping("/car")
public class ExController {
    @Autowired
    private ExRepository exRepository;
    
    /*
     * http://localhost:8080/car/input
     */
    @RequestMapping("/input")
    public String inputCarInfo() {
    
    	SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
    	TimeZone tz = TimeZone.getTimeZone("Greenwich");
    	dayTime.setTimeZone(tz);
    	String beanId = dayTime.format(new Date(System.currentTimeMillis()));
    	System.out.println("[ID]"+beanId);
    	
    	long id = Long.parseLong(beanId);
    	
    	//memo 세팅
    	Memo memo    = new Memo(id,"Relatively good condition is required for the tire replacement.");
    	
    	//ownerList 세팅
    	ArrayList<Owner> ownerList = new ArrayList<Owner>();
    	Owner owner1 = new Owner(id,"kim","seoul",3);
    	Owner owner2 = new Owner(id,"lee","pusan",2);
    	ownerList.add(owner1);
    	ownerList.add(owner2);

    	
    	//Appearance 세팅
		Status statusAppr   = new Status(id,1, "good");
		Appearance appr     = new Appearance(id,"sedan", "red", statusAppr);
    	
		//engine 세팅
    	Status statusEngine = new Status(id,2, "Relatively fine");
    	Engine engine       = new Engine(id,"two wheel","100","gasoline",statusEngine);
    	
    	//tire 세팅
    	Status statusTire   = new Status(id,3,"Needs replacement");
    	Tire   tire         = new Tire(id,"wide","michelin",statusTire);

    	//car 정보 세팅
        Car car = new Car(id,"소나타나","현기", "2016", ownerList, memo, appr, engine, tire);
        
        //객체 저장 영속화
        exRepository.save(car);

        return "success";
    }

    /*
     * LAZY 사용의 예 
     * 
     * http://localhost:8080/car/1
     * 
     */
    @RequestMapping("{id}")
    public Car getObj(@PathVariable Long id) {
    	
    	Car car = exRepository.findOne(id);

        String engineType  = car.getEngine().getType();
        System.out.println("[ENGINE_TYPE]"+engineType);
        
        System.out.println("\n====>LAZY get tire =============");
        String tireBrand  = car.getTire().getBrand();
        System.out.println("[TIRE_BRAND]"+tireBrand);
        
        
        System.out.println("====>FINISH ===================\n\n\n");
        
        return car;
    }
    
    
    /*
     * http://localhost:8080/car/appr/1
     */
    @RequestMapping("/appr/{id}")
    public Appearance getApprInfo(@PathVariable Long id) {
    	
    	Car car = exRepository.findOne(id);
    	
    	//다른 읽어오는 작업 없이 Car에서 Appearance 데이터를 가져와 사용할 수 있다.
        Appearance appr = car.getAppearance();
        
        System.out.println("color ===>"+appr.getColor());
        System.out.println("type ===>"+appr.getType());
        
        Status status = appr.getStatus();
        System.out.println("status_level ===>"+status.getLevel());
        System.out.println("status_message ===>"+status.getMessage());
        System.out.println("================================================");
        
        //만약 return을 Car를 하게되면 LAZY로 정의된 데이터의 접근이 일어나게 되서 Engine과 Tire의 데이터를 가져오는 각각의 쿼리가 진행된다. 
        return appr;
    }
    
    /*
     * http://localhost:8080/car/appr/1
     */
    @RequestMapping("/onlyappr/{id}")
    public Car getOnlyApprInfo(@PathVariable Long id) {

    	Car car = exRepository.findOne(id);
    	
    	//다른 읽어오는 작업 없이 Car에서 Appearance 데이터를 가져와 사용할 수 있다.
        Appearance appr = car.getAppearance();
        
        System.out.println("color ===>"+appr.getColor());
        System.out.println("type ===>"+appr.getType());
        
        Status status = appr.getStatus();
        System.out.println("status_level ===>"+status.getLevel());
        System.out.println("status_message ===>"+status.getMessage());
        
        System.out.println("getOwnerList ===>"+car.getOwnerList().size());
        System.out.println("================================================");

        return car;
    }
    
    
    @RequestMapping("/all")
    public List<Car> getObj() {
        
        List<Car> objList = exRepository.findAll();
        
        return objList;
    }
}
