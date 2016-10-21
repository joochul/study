package sample.carJpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity  //테이블과 매핑할 클래스임을 지정
@Table(name="CAR") //Entity가 연결될 테이블 명 지정
public class Car {
	
	/* 
	## 객체 구조 ##
	sample data 
	: {"carId":1,"name":"소나타나","brand":"현기","year":"2016","memo":{"memoId":1,"message":"Relatively good condition is required for the tire replacement"},"ownerList":[{"ownerId":1,"name":"kim","area":"seoul","period":3},{"ownerId":2,"name":"lee","area":"pusan","period":2}],"appearance":{"apprId":1,"type":"sedan","color":"red","status":{"statusId":1,"level":1,"message":"good"}},"engine":{"engineId":1,"type":"two wheel","power":"100","gas":"gasoline","status":null},"tire":{"tireId":1,"type":"wide","brand":"michelin","status":{"statusId":2,"level":3,"message":"Needs replacement"}}}
	
	[car]
		name, 
		brand, 
		year,
		
		[memo] 
			message
			
	    [owner(list)]
	    	name, 
	    	area, 
	    	period
	    	
	    [appearance] 
	    	type, 
	    	color,
	    	
	    	[status] 
	    		level, 
	    		message
	    		
	    [engine] 
	    	type, 
	    	power, 
	    	gas,
	    	
			[status] 
				level, 
				message
				
	    [tire] 
	    	type, 
	    	brand,
	    	
			[status] 
				level, 
				message
	*/
	
	/*
	 * [column]
	 * name : 해당 변수명을 지정한 column명으로 DB에 저장한다.
	 * nullable : null 가능 여부.
	 *  ex) @Column(name="car_id", nullable = false, length=20)
	 *  
	 * columnDefinition : DDL처럼 컬럼의 속성을 정의한다. 
	 *  ex) @Column(columnDefinition = "VARCHAR(40)")
	 */
    @Id
    @Column(name="car_id", nullable=false) //해당 변수명을 지정한 column명으로 DB에 저장한다.
    @GeneratedValue(strategy = GenerationType.AUTO) //값을 자동 생성한다.
    private long carId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String year;
    
    
    /*
     * 연결 id를 parent에서 생성해서 child로 넘기는 형태
     * 양방향 연결 예시
     * Car <=1==1=> memo
     * 
     * 양뱡향일 경우 각자의 객체에서 조회했을때 상대방 객체가 포함되어 보임    
     * 조회 Car  => json: {car, {memo}}
     * 조회 Memo => json: {memo,{car}}
     * 
     * 아래 설명 ) mappedBy를 이용해서 Car에서 발번한 ID를 memo가 FK 컬럼으로 관리 하는 것으로 세팅. 
     *        memo 객체내의 car라는 변수에 의해 매핑. car 변수에는 연결할 this객체를 세팅.
     * 
     ** 영속성 전이 ** 
     * CascadeType.ALL
     * CascadeType.PERSIST : 영속
     * CascadeType.MERGE   : 병합
     * CascadeType.REMOVE  : 삭제
     * CascadeType.REFRESH : reflash
     * CascadeType.DETACH  : detach
     *
     */
    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Memo memo;
    
    
    /*
     * 연결 id를 parent에서 생성해서 리스트의 child로 넘기는 형태
     * 양방향 연결 
     * Car <=1==n=> Owner
     * 
     * 조회 Car   => json: {car, ownerList:{owner1,owner2,...}}
     * 조회 Owner => json: {owner,{car}}
     * 
     * 아래 설명 ) mappedBy를 이용해서 Car에서 발번한 ID를 Owner가 FK 컬럼으로 관리 하는 것으로 세팅. 
     *        ownerList의 element인 Owner 객체내의 car라는 변수에 의해 매핑. car 변수에는 연결할 this객체를 세팅
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Owner> ownerList;
    
    
    /*
     * 연결 id를 child에서 생성해서 parent로 넘어오는 형태
     * 즉 FK관리를 Car에서 하게되는 상태
     * 
     * 단방향 연결 : car 입장에서는 Appearance객체의 appr_id를 통해 연결되어 있지만   
     *         Appearance입장에서는 Car객체와 연결이 없기때문에 연결 관계를 알 수 없다.
     * Car =1==1=> Appearance
     * 
     * 조회 Car        => json: {car, {appearance}}
     * 조회 Appearance => json: {appearance}
     * 
     * [주요]fetch : 해당 객체를 find할 경우 연결된 객체까지 동시에 가져올 것인가를 결정해야 함
     *   => FetchType.EAGER : 즉시로딩 - 상위 객체가 호출될때 같이 호출됨. 상위 테이블과 join을 통해서 하나의 query로 데이터를 가져오게됨
     *                               자주사용하는 데이터일 경우 한번에 가져오는 것이 유리함
     *   => FetchType.LAZY : 지연로딩 - 상위 객체가 호출될때 데이터를 가저오지 않고 기다림. 해당 객체가 필요할때 그때 단일 query를 통해서 데이터를 가져옴
     *                               자주 사용하지 않아서 미리 가져올 필요가 없는 데이터일 경우 유리함
     *                               
	 * optional : 컬럼의 데이터가 없을 경우 무시하고 데이터를 입력하고자 할때는 'true', 무조건 데이터가 있어야 할 경우에는 'false'
     */
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch=FetchType.EAGER) //즉시로딩
    @JoinColumn(name="appr_id", nullable = false, unique = true, insertable = true, updatable = false)
    private Appearance appearance;
    
    
    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch=FetchType.LAZY) //지연로딩
    @JoinColumn(name="engine_id", nullable = true, unique = true, insertable = true, updatable = false)
    private Engine engine;
    
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch=FetchType.LAZY) //지연로딩
    @JoinColumn(name="tire_id", nullable = true, unique = true, insertable = true, updatable = false)
    private Tire tire;
    
    
    /*
     * constructor
     */
    public Car() {
    }
    
    public Car(String name, String brand, String year, Appearance appearance, Engine engine, Tire tire) {
        this.name       = name;
        this.brand      = brand;
        this.year       = year;
        this.appearance = appearance;
        this.engine     = engine;
        this.tire       = tire;
    }
    
    public Car(String name, String brand, String year, List<Owner> ownerList, Memo memo, Appearance appearance, Engine engine, Tire tire) {
        this.name       = name;
        this.brand      = brand;
        this.year       = year;
        this.ownerList  = ownerList;
        this.memo       = memo;
        this.appearance = appearance;
        this.engine     = engine;
        this.tire       = tire;
    }

    
    //set get
	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Owner> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<Owner> ownerList) {
		this.ownerList = ownerList;
	}

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Tire getTire() {
		return tire;
	}

	public void setTire(Tire tire) {
		this.tire = tire;
	}
	
}
