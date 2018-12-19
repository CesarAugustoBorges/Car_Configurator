package Business.Encomenda;

import Business.Stock.Peca;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Encomenda {
    private int id;
    private ArrayList<LinhaDeEncomenda> linhasDeEncomenda;
    private String status;

    public Encomenda(int id, ArrayList<LinhaDeEncomenda> les) {
        this.status = "";
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

    public void addPacote(PacoteDeConfiguracao p) {
        LinhaDeEncomendaPacote lep = new LinhaDeEncomendaPacote(linhasDeEncomenda.size()+1, 1, p);
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            if(le.hasSameProduct(lep))
                return;
        this.linhasDeEncomenda.add(lep);
    }

    public void addLinhaEmcomenda(LinhaDeEncomenda le) {
        this.linhasDeEncomenda.add(le);
    }

    public void removeLinhaEncomenda(LinhaDeEncomenda le) {
        this.linhasDeEncomenda.remove(le);
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

    public String checkStatusWhenAddding(Peca p) {
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

    public String checkStatusWhenAddding(PacoteDeConfiguracao pdc)  {
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

    // Se um pacote tiver um item na lista, então o pacote é removido
    public void removePeças(List<Integer> ids) {
        for(int i = 0; i < linhasDeEncomenda.size(); i++) {
            for(int j = 0; j < ids.size(); j++) {
                if(linhasDeEncomenda.get(i).hasPeca(j)){
                    this.linhasDeEncomenda.remove(i);
                    i--; j = ids.size();
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
