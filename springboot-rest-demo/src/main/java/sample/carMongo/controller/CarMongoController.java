package sample.carMongo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.carMongo.entity.Appearance;
import sample.carMongo.entity.Car;
import sample.carMongo.entity.Engine;
import sample.carMongo.entity.Memo;
import sample.carMongo.entity.Owner;
import sample.carMongo.entity.Status;
import sample.carMongo.entity.Tire;
import sample.carMongo.repository.CarMongoRepository;

@RestController
@RequestMapping("/carmongo")
public class CarMongoController {
    @Autowired
    private CarMongoRepository exRepository;
    
    
    
    /*
     * 모든 car 리스트 가져오기
     * 
     * http://localhost:8080/carmongo
     */
    @RequestMapping("")
    public List<Car> getObj() {
        
        List<Car> objList = exRepository.findAll();
        
        return objList;
    }
    
    /*
     * car 데이터 입력
     * 
     * 테스트를 위한 데이터 강제 세팅
     * 
     * http://localhost:8080/carmongo/input
     */
    @RequestMapping(method = RequestMethod.POST, value = "/input")
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
	 * ID로 car 데이터 delete
	 * 
     * http://localhost:8080/car/1
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Map<String, Object> deleteBook(@PathVariable("id") Long id) {
	
		exRepository.delete(id);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Car delete successfully");
		return response;
	}

    
    /*
     * ID로 car 데이터 가져오기
     * 
     * http://localhost:8080/carmongo/1
     * 
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Car getObj(@PathVariable Long id) {
    	
    	Car car = exRepository.findOne(id);

    	//TEST
        String engineType  = car.getEngine().getType();
        System.out.println("[ENGINE_TYPE]"+engineType);
        
        //TEST
        String tireBrand  = car.getTire().getBrand();
        System.out.println("[TIRE_BRAND]"+tireBrand);
        
        return car;
    }
    
    
    /*
     * ID로 car의 entry인 apprearance 정보를 가져오기
     * 
     * http://localhost:8080/carmongo/appr/1
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
    
}
