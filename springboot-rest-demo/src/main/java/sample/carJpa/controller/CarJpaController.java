package sample.carJpa.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.carJpa.entity.Appearance;
import sample.carJpa.entity.Car;
import sample.carJpa.entity.Engine;
import sample.carJpa.entity.Memo;
import sample.carJpa.entity.Owner;
import sample.carJpa.entity.Status;
import sample.carJpa.entity.Tire;
import sample.carJpa.repository.CarJpaRepository;
import sample.carJpa.repository.MemoJpaRepository;
import sample.carJpa.repository.OwnerJpaRepository;

@RestController
@RequestMapping("/carjpa")
public class CarJpaController {
    @Autowired
    private CarJpaRepository carRepository;
    
    @Autowired
    private MemoJpaRepository memoRepository;
    
    @Autowired
    private OwnerJpaRepository ownerRepository;
    
    /*
     * 모든 car 리스트 가져오기
     * 
     * http://localhost:8080/carjpa 
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public List getObj() {
        
        List objList = carRepository.findAll();
        
        return objList;
    }
    
    
    /*
     * car 데이터 입력
     * 
     * 테스트를 위한 데이터 강제 세팅
     * 
     * http://localhost:8080/carjpa/input
     */
    @RequestMapping(method = RequestMethod.POST, value = "/input")
    public String inputCarInfo() {
    
    	//memo 세팅
    	Memo memo    = new Memo("Relatively good condition is required for the tire replacement");
    	
    	//ownerList 세팅
    	ArrayList<Owner> ownerList = new ArrayList<Owner>();
    	Owner owner1 = new Owner("kim","seoul",3);
    	Owner owner2 = new Owner("lee","pusan",2);
    	ownerList.add(owner1);
    	ownerList.add(owner2);

    	
    	//Appearance 세팅
		Status statusAppr   = new Status(1, "good");
		Appearance appr     = new Appearance("sedan", "red", statusAppr);
    	
		/*
		 * engine 세팅
    	 * Status statusEngine = new Status(2, "Relatively fine");
    	 * Engine engine       = new Engine("two wheel","100","gasoline",statusEngine);
		 *
    	 * 만약 status 변수가 있지만  세팅할 값이 없을 경우 ==> optional : 해당 컬럼이 Null일 경우 제외여부, true일경우 제외가능
    	 * status 값을 세팅하지 않고 입력 가능 여부 확인
    	 */
		Engine engine = new Engine();
		engine.setType("two wheel");
		engine.setPower("100");
		engine.setGas("gasoline");
    	
    	//tire 세팅
    	Status statusTire   = new Status(3,"Needs replacement");
    	Tire   tire         = new Tire("wide","michelin",statusTire);

    	//car 정보 세팅
        //memo와 appr은 세팅하지 않아도 memo를 save 한 순간 car와 연결된다.
        Car car = new Car("소나타나","현기", "2016", appr, engine, tire);
        
        memo.setCar(car); //memo 양방향 연결
        owner1.setCar(car);//owner 양방향 연결
        owner2.setCar(car);//owner 양방향 연결
        
        //객체 저장 영속화
        //car가 생성되면서 발번한 id를 memo와 owner가 사용하기 때문에 car를 먼저 저장하고
        //memo와 owner를 저장한다.
        
        carRepository.save(car);
        
        memoRepository.save(memo);        
        ownerRepository.save(owner1); 
        ownerRepository.save(owner2);
        
        /*
         [한번에 저장]
       	 Car car = new Car("소나타나","현기", "2016", ownerList, memo, appr, engine, tire);
       	 carRepository.save(car);
       	 
       	 car 객체를 모두 완성시키고 나서 save를 하면 JPA가 알아서 위와 같이 각각 저장해 준다.
       	 하지만 정석은 위와 같은 구조로 처리 하는 것이다.
         */

        return "success";
    }

    
    
	/*
	 * ID로 car 데이터 delete
	 * 
     * http://localhost:8080/carjpa/1
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public Map<String, Object> deleteBook(@PathVariable("id") Long id) {
	
		carRepository.delete(id);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Car delete successfully");
		return response;
	}
	
	
    /*
     * ID로 car 데이터 가져오기
     * 
     * LAZY 사용의 예 
     * 
     * http://localhost:8080/carjpa/1
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Car getObj(@PathVariable Long id) {
    	
    	/*
    	 * 최초 car 정보를 찾아서 로딩함
    	 * 
    	 * EAGER로 세팅된  Appearance는 find 시에 로딩됨
    	 * 하지만 LAZY로 선언된 Engine과 Tire는 로딩하지 않음
    	 *  
    	 * (query 참조)
    	 * ----------------------------------------------------
			select ...
  			  from car car0_ inner join appearance appearance1_ on car0_.appr_id=appearance1_.appr_id 
                             left outer join status status2_ on appearance1_.status_id=status2_.status_id 
                             left outer join memo memo3_ on car0_.car_id=memo3_.car_id 
             where car0_.car_id=?
    	 * ----------------------------------------------------
    	 */
    	Car car = carRepository.findOne(id);

    	/*
    	 * Engine 객체에 접근할때 해당 데이터를 가져오기위한 query 구동
    	 * 
    	 * (query 참조)
    	 * ----------------------------------------------------
    	 * select ...
             from engine engine0_ inner join status status1_ on engine0_.status_id=status1_.status_id 
            where engine0_.engine_id=?
    	 * ----------------------------------------------------
    	 */
        System.out.println("\n====>LAZY get engine =======");
        String engineType  = car.getEngine().getType();
        System.out.println("[ENGINE_TYPE]"+engineType);
        
        /*
    	 * Tire 객체에 접근할때 해당 데이터를 가져오기위한 query 구동
    	 * 
    	 * (query 참조)
    	 * ----------------------------------------------------
    	 * select...
             from tire tire0_ inner join status status1_ on tire0_.status_id=status1_.status_id 
            where tire0_.tire_id=?
    	 * ----------------------------------------------------
    	 */
        System.out.println("\n====>LAZY get tire =============");
        String tireBrand  = car.getTire().getBrand();
        System.out.println("[TIRE_BRAND]"+tireBrand);
        
        
        System.out.println("====>FINISH ===================\n\n\n");
        
        /*
         * 최종 적으로 Car 객체를 리턴하면서 OneToMany의 ownerList를 가져온다.
         * 다른 컬럼들은 default가 즉시로딩(EAGER)인 반면 OneToMany인 OwnerList는 지연로딩(LAZY)로 불러오고있다.
         * 성능의 이슈로 자체적으로 세팅되는 것으로 보인다.
         * 
         * select ...
             from owner ownerlist0_ 
            where ownerlist0_.car_id=?
         */
        
        return car;
    }
    
    
    /*
     * ID로 car의 entry인 apprearance 정보를 가져오기
     * 
     * EAGER 사용의 예
     * 
     * http://localhost:8080/carjpa/appr/1
     */
    @RequestMapping(method = RequestMethod.GET, value = "/appr/{id}")
    public Appearance getApprInfo(@PathVariable Long id) {
    	
    	/*
    	 * car를 가져올때 EAGER는 즉시로딩을 통해 미리 가져왔다.
    	 * 
    	 * select ... 
             from car car0_ inner join appearance appearance1_ on car0_.appr_id=appearance1_.appr_id 
                            inner join status status2_ on appearance1_.status_id=status2_.status_id 
                            left outer join memo memo3_ on car0_.car_id=memo3_.car_id 
            where car0_.car_id=? 
    	 */
    	System.out.println("\n====>EAGER get Appearance ===================");
    	Car car = carRepository.findOne(id);
    	
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
