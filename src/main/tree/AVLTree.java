package tree;

import java.util.ArrayList;

import estrut.No;
import estrut.Tree;

public class AVLTree implements Tree{

    private No raiz;

    void calcAltura(No n){
        n.setAltura(1 + Math.max(altura(n.getMenor()), altura(n.getMaior())));
    }
    
    private int altura(No n){
        return n == null ? -1 : n.getAltura();
    }

    int getBalanceamento(No n) {
        return (n == null) ? 0 : altura(n.getMaior()) - altura(n.getMenor());
    }

    No rotaDireita(No y) {
        No x = y.getMenor();
        No z = x.getMaior();
        x.setMaior(y);
        y.setMenor(z);
        calcAltura(y);
        calcAltura(x);
        return x;
    }

    No rotaEsquerda(No y) {
        No x = y.getMaior();
        No z = x.getMenor();
        x.setMenor(y);
        y.setMaior(z);
        calcAltura(y);
        calcAltura(x);
        return x;
    }

    No rebalancear(No z) {
        calcAltura(z);
        int balance = getBalanceamento(z);
        if (balance > 1) {
            if (altura(z.getMaior().getMaior()) > altura(z.getMaior().getMenor())) {
                z = rotaEsquerda(z);
            } else {
                z.setMaior(rotaDireita(z.getMaior()));
                z = rotaEsquerda(z);
            }
        } else if (balance < -1) {
            if (altura(z.getMenor().getMenor()) > altura(z.getMenor().getMaior()))
                z = rotaDireita(z);
            else {
                z.setMenor(rotaEsquerda(z.getMenor()));
                z = rotaDireita(z);
            }
        }
        return z;
    }

    @Override
    public boolean buscaElemento(int valor) {
        No x = buscaRecursiva(valor);
        if(x != null){
            if(x.getValor() == valor){
                return true;
            }
        }
        return false;
    }

    private No buscaRecursiva(int valor){
        No atual = raiz;
        while (atual != null) {
            if (atual.getValor() == valor) {
                break;
            }
            atual = atual.getValor() < valor ? atual.getMaior() : atual.getMenor();
        }
        return atual;
    }

    @Override
    public int minimo() {
        return 0;
    }

    @Override
    public int maximo() {
        return maxRec(raiz).getValor();
    }

    private No maxRec(No n){
        if(n.getMaior() != null){
            return maxRec(n.getMaior());
        }
        else{
            return n;
        }
    }

    @Override
    public void insereElemento(int valor) {
        raiz = insereRecursivo(raiz, valor);
    }

    private No insereRecursivo(No raiz, int valor) {
        if (raiz == null) {
            return new No(valor);
        } else if (raiz.getValor() > valor) {
            raiz.setMenor(insereRecursivo(raiz.getMenor(), valor));
        } else if (raiz.getValor() < valor) {
            raiz.setMaior(insereRecursivo(raiz.getMaior(), valor));
        } else {
            System.out.println("Valor duplicado!");
        }
        return rebalancear(raiz);
    }

    @Override
    public void remove(int valor) {
        removeRecursivo(raiz, valor);
    }

    


    private No removeRecursivo(No no, int valor) {
        if (no == null) {
            return no;
        } else if (no.getValor() > valor) {
            no.setMenor(removeRecursivo(no.getMenor(), valor));
        } else if (no.getValor() < valor) {
            no.setMaior(removeRecursivo(no.getMaior(), valor));
        } else {
            if (no.getMenor() == null || no.getMaior() == null) {
                no = (no.getMenor() == null) ? no.getMaior() : no.getMenor();
            } else {
                No menorFilho = menorFilho(no.getMaior());
                no.setValor(menorFilho.getValor());
                no.setMaior(removeRecursivo(no.getMaior(), no.getValor()));
            }
        }
        if (no != null) {
            no = rebalancear(no);
        }
        return no;
    }

    private No menorFilho(No no) {
        if(no.getMenor() == null){
            return no;
        }else{
            return menorFilho(no.getMenor());
        }        
    }

    @Override
    public int[] preOrdem() {
        ArrayList<Integer> arvore = new ArrayList<>();
        preOrdemRecursivo(this.raiz, arvore); 
        int[] tree = new int[arvore.size()];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = arvore.get(i);
        }
        return tree;
    }

    private void preOrdemRecursivo(No raiz, ArrayList<Integer> arvore) {
        if(raiz != null){
            arvore.add(raiz.getValor()); 
            preOrdemRecursivo(raiz.getMenor(), arvore);
            preOrdemRecursivo(raiz.getMaior(), arvore);
        }
    }

    @Override
    public int[] emOrdem() {
        ArrayList<Integer> arvore = new ArrayList<>();
        emOrdemRecursivo(this.raiz, arvore); 
        int[] tree = new int[arvore.size()];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = arvore.get(i);
        }
        return tree;
    }

    //método recursivo que cria um arraylist em ordem da árvore sendo analisada
    private void emOrdemRecursivo(No raiz, ArrayList<Integer> arvore){
        if(raiz != null){
            emOrdemRecursivo(raiz.getMenor(), arvore);
            arvore.add(raiz.getValor()); 
            emOrdemRecursivo(raiz.getMaior(), arvore);
        }
    } 
    @Override
    public int[] posOrdem() {
        ArrayList<Integer> arvore = new ArrayList<>();
        posOrdemRecursivo(this.raiz, arvore); 
        int[] tree = new int[arvore.size()];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = arvore.get(i);
        }
        return tree;
    }

    private void posOrdemRecursivo(No raiz, ArrayList<Integer> arvore) {
        if(raiz != null){
            posOrdemRecursivo(raiz.getMenor(), arvore);
            posOrdemRecursivo(raiz.getMaior(), arvore);
            arvore.add(raiz.getValor()); 
        }
    }

}