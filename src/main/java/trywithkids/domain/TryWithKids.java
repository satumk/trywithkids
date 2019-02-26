
package trywithkids.domain;

import java.util.List;


public class TryWithKids {
    //Ilmentymä muistaa tapahtumia-kentän suurimman arvon
        private List<Experiment> experiments;
        
	private int tapahtumia, maxTapahtumia;
	public TryWithKids(int alkuarvo){
		if(alkuarvo < 0)
			return;
		this.tapahtumia = alkuarvo;
		this.maxTapahtumia = alkuarvo;
	}
	public TryWithKids(){
		this.tapahtumia = 0;
	}

  	public void lisaa(){
    		this.tapahtumia = this.tapahtumia + 1;
		if(tapahtumia > this.maxTapahtumia)
			this.maxTapahtumia = tapahtumia; 
  	}
  	public int lukema(){
    		return this.tapahtumia;
  	}
  	public void nollaa(){
    		this.tapahtumia = 0;
  	}
        @Override
	public String toString(){
		return "Laskurin lukema on " + this.tapahtumia + " (max. " + this.maxTapahtumia +")";
	}

}
