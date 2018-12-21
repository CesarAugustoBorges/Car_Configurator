package Business.Encomenda;

import Business.Stock.Peca;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Encomenda {
    private int id;
    private List<LinhaDeEncomenda> linhasDeEncomenda;
    private String status;

    public Encomenda() {
        this.status = "Valid";
        this.linhasDeEncomenda = new ArrayList<>();
    }

    public Encomenda(int id, ArrayList<LinhaDeEncomenda> les) {
        this.status = "Valid";
        this.setId(id);
        this.setLinhasDeEncomenda(les);
    }

    // adiciona uma peça como uma linha de encomenda, caso a peça já esteja numa LinhaDeEncomendaPeca, a quantidade da LE é incrementada
    public void addPeca(Peca p, int quantidade){
        LinhaDeEncomendaPeca lep = new LinhaDeEncomendaPeca(linhasDeEncomenda.size()+1, quantidade, p);
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            if(le.hasSameProduct(lep)){
                le.setQuantidade(le.getQuantidade()+quantidade);
                return;
            }
        this.linhasDeEncomenda.add(lep);
    }

    public void addPecas(List<Peca> ps){
        for(Peca p: ps)
            addPeca(p, 1);
    }


    public void addPacote(PacoteDeConfiguracao p) {
        LinhaDeEncomendaPacote lep = new LinhaDeEncomendaPacote(linhasDeEncomenda.size()+1, 1, p);
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            if(le.hasSameProduct(lep)){
                le.setQuantidade(le.getQuantidade()+1);
                return;
            }
        this.linhasDeEncomenda.add(lep);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LinhaDeEncomenda> getLinhasDeEncomenda() {
        ArrayList<LinhaDeEncomenda> res = new ArrayList<>();
        for(LinhaDeEncomenda le : this.linhasDeEncomenda)
            res.add(le.clone());
        return res;
    }

    public void setLinhasDeEncomenda(ArrayList<LinhaDeEncomenda> linhasDeEncomenda) {
        this.linhasDeEncomenda = new ArrayList<>();
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            this.linhasDeEncomenda.add(le);
    }


    public List<Pair<Integer,String>> getLEIncompativeisCom(Peca p){
        ArrayList<Integer> incom = p.getIncompatibilidades();
        List<Pair<Integer,String>> res = new ArrayList<>();
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            for(Integer inc : incom)
                if(le.hasPeca(inc))
                    res.add(new Pair<>(le.getId(), le.getDescricao()));
        return res;
    }

    public void removePeca(int id) {
        for(LinhaDeEncomenda le: linhasDeEncomenda) {
            if(le.hasPeca(id))
                this.linhasDeEncomenda.remove(le);
        }
    }

    // Se um pacote tiver um item na lista, então o pacote é removido
    public void removePecas(List<Integer> ids) {
        for(Integer id: ids)
            removePeca(id);
    }

    public void removeLsE(List<Integer> ids){
        for(LinhaDeEncomenda le : linhasDeEncomenda){
            if(ids.contains(le.getId()))
                linhasDeEncomenda.remove(le);
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pair<Integer, String>> getLsEDependentesPacote(PacoteDeConfiguracao p){
        List<Integer> = p.ge
        List<Pair<Integer, String>> res = new ArrayList<>();
        for(LinhaDeEncomenda le: linhasDeEncomenda)

    }

    public float getPreco() {
        float preco = 0.0f;
        for(LinhaDeEncomenda le : linhasDeEncomenda)
            preco += le.getPrecoTotal();
        return preco;
    }

    public String getFatura() {
        StringBuilder sb = new StringBuilder();
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            sb.append(le.getDescricao() + " ------- " + le.getQuantidade() + " - " + le.getPrecoTotal() + "\n");
        return sb.toString();
    }


    /*
    public String checkStatusWhenAdding(Peca p) {
        ArrayList<Integer> dependencias = p.getDependencias();
        ArrayList<Integer> incompatibilidades = p.getIncompatibilidades();
        for(LinhaDeEncomenda le: linhasDeEncomenda) {
            for(Integer dep : dependencias)
                if(le.hasPeca(dep))
                    dependencias.remove(Integer.valueOf(dep));
            for(Integer inc : incompatibilidades){
                if(le.hasPeca(inc))
                    if(this.status.indexOf("Incompativel") < 0)
                        this.status = "Incompativel";
            }
        }
        if(dependencias.size() > 0 && this.status.indexOf("Dependente") < 0)
            this.status += "Dependente";

        if(this.status == "") this.status = "Valid";
        return this.status;
    }

    public String checkStatusWhenAdding(PacoteDeConfiguracao pdc)  {
        Set<Integer> incompatibilidades = pdc.getIncompatibilidades();
        for(LinhaDeEncomenda le: linhasDeEncomenda) {
            for(Integer inc : incompatibilidades){
                if(le.hasPeca(inc))
                    if(this.status.indexOf("Incompativel") < 0)
                        this.status = "Incompativel";
            }
        }
        if(this.status == "") this.status = "Valid";
        return this.status;
    }
    */
    public static void main(String[] args) {

    }
}
