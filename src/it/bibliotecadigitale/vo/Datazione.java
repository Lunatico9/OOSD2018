package it.bibliotecadigitale.vo;

public class Datazione {
	
	private int anno;
	private String value;
	
	public Datazione(int anno) {
		this.anno = anno;
		this.value = "N.P.";
	    int y = 2100; 
		String c = "d.C.";
		
		for (int x = 21; x > -40; x--) {
			if (y < 0) {
				c = "a.C.";
			}
			if (anno < y && anno >= y-100) {
				int x1 = x - 2*x;
				this.value = x1 + "° secolo " + c;
			}
			y -= 100;
		}
	}
	
	public int getAnno() {
		return this.anno;
	}
	
	public String getDatazione() {
		return this.value;
	}
	
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	public void setDatazione(int anno) {
		this.value = "N.P.";
	    int y = 2100; 
		String c = "d.C.";
		for (int x = 21; x > -40; x--) {
			if (y < 0) {
				c = "a.C.";
			}
			if (anno < y && anno >= y-100) {
				int x1 = x - 2*x;
				this.value = x1 + "° secolo " + c;
			}
			y -= 100;
		}
	}
		
}

