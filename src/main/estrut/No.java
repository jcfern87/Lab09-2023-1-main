package estrut;

public class No {
    int valor;
    No esquerda;
    No direita;
    int altura;

    public No(int valor){
        this.valor = valor;
    }

    public No getMaior(){
        return this.direita;
    }
    public void setMaior(No n){
        this.direita = n;
    }
    public No getMenor(){
        return this.esquerda;
    }
    public void setMenor(No n){
        this.esquerda = n;
    }
    public int getValor(){
        return this.valor;
    }
    public void setValor(int valor){
        this.valor = valor;
    }
    public int getAltura(){
        return this.altura;
    }
    public void setAltura(int valor){
        this.altura = valor;
    }
}
