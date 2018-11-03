package application.util;

import javax.validation.constraints.NotNull;

import javafx.scene.image.Image;

public class Product {
	@NotNull
	private int code_product;
	@NotNull
	private String label;
	@NotNull
	private int price;
	
	private Image img;
	
	private String description;
	
	private int quantityCart = 0;
	
	private int stock;
	
	public Product(int code_product, String label, int price, String description){
		this.code_product = code_product;
		this.label = label;
		this.price = price;
		this.description = description;
	}

	public int getCode_product() {
		return code_product;
	}

	public void setCode_product(int code_product) {
		this.code_product = code_product;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantityCart() {
		return quantityCart;
	}

	public void setQuantityCart(int quantityCart) {
		this.quantityCart = quantityCart;
	}

	public int getStock(int code_shop) {
		return Shop.getStock(code_shop, code_product);
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
