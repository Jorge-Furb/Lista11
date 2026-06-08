package com.example.Lista11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Map<Integer,Municipio> municipios = new HashMap<>();
	
	
	public static void fillMunicipios(String diretorio) {
		File file = new File(diretorio);
        try {
			FileInputStream fis = new FileInputStream(file);
			if(!file.exists()) {
				throw new FileNotFoundException("Arquivo nao encontrado");
			}
			byte[] bytes= fis.readAllBytes();
			int auxContInicioDados=0;
			for(byte b : bytes) {
				
				if((b&0xFF)==0x0A) { // byte no Java é sempre signed, converter pra unsigned sempre que for comparar valor > 0x80
									// ou subtrair 256 do valor comparado para tornar o codigo ilegivel de sacanagem
					break;
				}
				auxContInicioDados++;
			}
			auxContInicioDados++;
			int auxCampo=0;
			String nomeCidade=null;
			String nomeEstado=null;
			int populacao=0;
			Integer codigoCidade=null;
			
			for(int i = auxContInicioDados;i<bytes.length;i++) {
				if((bytes[i]&0xFF)==0x0D||(bytes[i]&0xFF)==0x0A) {
					if(auxCampo!=0) {
						municipios.put(codigoCidade, new Municipio(nomeCidade,nomeEstado,populacao)); //Jeito porco de evitar duplicidade
					}
					auxCampo=0;
					continue;
				}
				
				String dadoCampo="";
				int aux=0;
				while(i<bytes.length&&(bytes[i]&0xFF)!=0x3B&&(bytes[i]&0xFF)!=0x0D) {					
					aux++;
					i++;
				}
				
				byte[] dadoCampoByte= new byte[aux];
				System.arraycopy(bytes, i-aux, dadoCampoByte, 0, aux);
				dadoCampo=new String(dadoCampoByte,StandardCharsets.UTF_8).replace("\u00A0","");
				switch (auxCampo) {
				case 0:
					codigoCidade=Integer.parseInt(dadoCampo);
					//System.out.println("Codigo cidade "+codigoCidade);
					break;
				case 1:
					nomeCidade=dadoCampo.trim();
					//System.out.println("Nome cidade "+nomeCidade);
					break;
				case 2:
					nomeEstado=dadoCampo.trim();
					//System.out.println("Nome estado "+nomeEstado);
					break;
					
				case 3:
					populacao=Integer.parseInt(dadoCampo);
					//System.out.println("Populacao "+populacao);
					break;
				default:
					break;
				}
				auxCampo++;
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printMenorEMaiorPopulacao() {
		int maior=-1;
		int menor=-1;
		String maiorCidade=null;
		String menorCidade=null;
		
		for(Map.Entry<Integer,Municipio> m : municipios.entrySet()) {
			if(m.getValue().getPopulacao()<menor||menor==-1) {
				menor=m.getValue().getPopulacao();
				menorCidade=m.getValue().getNome()+" - "+m.getValue().getEstado();
			}
			if(m.getValue().getPopulacao()>maior) {
				maior=m.getValue().getPopulacao();
				maiorCidade=m.getValue().getNome()+" - "+m.getValue().getEstado();
			}
		}
		System.out.println("Maior populacao: "+ maiorCidade + " com "+maior+" habitantes\nMenor populacao: "+menorCidade+" com "+menor+" habitantes");
	}
    public static void main( String[] args )
    {
        String diretorio = ".\\Ressources\\L11_municipios.csv";
        fillMunicipios(diretorio);
        printMenorEMaiorPopulacao();
        	
    }
    
}
