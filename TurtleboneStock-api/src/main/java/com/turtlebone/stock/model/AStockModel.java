package com.turtlebone.stock.model;
import java.io.Serializable;
import java.util.Date;

public class AStockModel implements Serializable{
	
	private Integer id;
	private String code;
	private String name;
	private Date datetime;
	private Double changepercentage;
	private Double change;
	private Double price;
	private Long volume;
	private Double turnoverrate;
	private Double startprice;
	private Double topprice;
	private Double lowprice;
	private Double preprice;
	private Double pe;
	private Long totalamount;
	private Double qrr;
	private String category;
	private Double amplitude;
	private Long buy;
	private Long sell;
		
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
		
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
		
	public void setDatetime(Date datetime){
		this.datetime = datetime;
	}
	
	public Date getDatetime(){
		return this.datetime;
	}
		
	public void setChangepercentage(Double changepercentage){
		this.changepercentage = changepercentage;
	}
	
	public Double getChangepercentage(){
		return this.changepercentage;
	}
		
	public void setChange(Double change){
		this.change = change;
	}
	
	public Double getChange(){
		return this.change;
	}
		
	public void setPrice(Double price){
		this.price = price;
	}
	
	public Double getPrice(){
		return this.price;
	}
		
	public void setVolume(Long volume){
		this.volume = volume;
	}
	
	public Long getVolume(){
		return this.volume;
	}
		
	public void setTurnoverrate(Double turnoverrate){
		this.turnoverrate = turnoverrate;
	}
	
	public Double getTurnoverrate(){
		return this.turnoverrate;
	}
		
	public void setStartprice(Double startprice){
		this.startprice = startprice;
	}
	
	public Double getStartprice(){
		return this.startprice;
	}
		
	public void setTopprice(Double topprice){
		this.topprice = topprice;
	}
	
	public Double getTopprice(){
		return this.topprice;
	}
		
	public void setLowprice(Double lowprice){
		this.lowprice = lowprice;
	}
	
	public Double getLowprice(){
		return this.lowprice;
	}
		
	public void setPreprice(Double preprice){
		this.preprice = preprice;
	}
	
	public Double getPreprice(){
		return this.preprice;
	}
		
	public void setPe(Double pe){
		this.pe = pe;
	}
	
	public Double getPe(){
		return this.pe;
	}
		
	public void setTotalamount(Long totalamount){
		this.totalamount = totalamount;
	}
	
	public Long getTotalamount(){
		return this.totalamount;
	}
		
	public void setQrr(Double qrr){
		this.qrr = qrr;
	}
	
	public Double getQrr(){
		return this.qrr;
	}
		
	public void setCategory(String category){
		this.category = category;
	}
	
	public String getCategory(){
		return this.category;
	}
		
	public void setAmplitude(Double amplitude){
		this.amplitude = amplitude;
	}
	
	public Double getAmplitude(){
		return this.amplitude;
	}
		
	public void setBuy(Long buy){
		this.buy = buy;
	}
	
	public Long getBuy(){
		return this.buy;
	}
		
	public void setSell(Long sell){
		this.sell = sell;
	}
	
	public Long getSell(){
		return this.sell;
	}
		
		
}