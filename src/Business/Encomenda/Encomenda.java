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
        int id = linhasDeEncomenda.size() > 0 ? linhasDeEncomenda.get(linhasDeEncomenda.size() -1).getId() + 1 : 1;
        LinhaDeEncomendaPeca lep = new LinhaDeEncomendaPeca(id, quantidade, p);
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
        int id = linhasDeEncomenda.size() > 0 ? linhasDeEncomenda.get(linhasDeEncomenda.size() -1).getId() + 1 : 1;
        LinhaDeEncomendaPacote lep = new LinhaDeEncomendaPacote(id, 1, p);
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

    public void setStatus(String s){
        this.status = s;
    }

    public LinhaDeEncomenda getLinhaEncomenda(int id){
        int i;
        for(i = 0; i < linhasDeEncomenda.size() && linhasDeEncomenda.get(i).getId() != id; i++);
        if(i != linhasDeEncomenda.size()) return linhasDeEncomenda.get(i);
        return null;
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

    // Devolve uma lista com os ids e as descrições das linhas de encomenda que são incompativeis com a peça p
    public List<Pair<Integer,String>> getLEIncompativeisCom(Peca p){
        ArrayList<Integer> incom = p.getIncompatibilidades();
        List<Pair<Integer,String>> res = new ArrayList<>();
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            for(Integer inc : incom)
                if(le.hasPeca(inc))
                    res.add(new Pair<>(le.getId(), le.getDescricao()));
        return res;
    }

    // Devolve uma lista com os ids e as descrições das linhas de encomenda que são incompativeis com o pacote p
    public List<Pair<Integer,String>> getLEIncompativeisCom(PacoteDeConfiguracao p){
        Set<Integer> incom = p.getIncompatibilidades();
        List<Pair<Integer,String>> res = new ArrayList<>();
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            for(Integer inc : incom)
                if(le.hasPeca(inc))
                    res.add(new Pair<>(le.getId(), le.getDescricao()));
        return res;
    }

    public void removePeca(int id, boolean desfazer) {
        int i;
        for(i = 0; i < linhasDeEncomenda.size(); i++) {
            if(linhasDeEncomenda.get(i).hasPeca(id))
                if(!desfazer)
                    break;
                else break; // alterar o else
        }
        if(i != linhasDeEncomenda.size())
            this.linhasDeEncomenda.remove(i);
    }

    public void removePacote(int id, boolean desfazer) {
        int i;
        for(i = 0; i < linhasDeEncomenda.size(); i++) {
            if(linhasDeEncomenda.get(i).hasPeca(id))
                if(!desfazer)
                    break;
                else break; // alterar o else
        }
        if(i != linhasDeEncomenda.size())
            this.linhasDeEncomenda.remove(i);
    }


    public void removeLsE(List<Integer> ids){
        for(LinhaDeEncomenda le : linhasDeEncomenda){
            if(ids.contains(le.getId()))
                linhasDeEncomenda.remove(le);
        }
    }

    public List<Pair<Integer, String>> getLsEDependentes(PacoteDeConfiguracao p){
        List<Pair<Integer, String>> res = new ArrayList<>();
        int count = 0;
        for(LinhaDeEncomenda le: linhasDeEncomenda){
            if(le.dependeDe(p))
                res.add(new Pair<>(le.getId(), le.getDescricao()));
        }
        return res;
    }

    public List<Pair<Integer, String>> getLsEDependentes(Peca p){
        List<Pair<Integer, String>> res = new ArrayList<>();
        int count = 0;
        for(LinhaDeEncomenda le: linhasDeEncomenda){
            if(le.dependeDe(p))
                res.add(new Pair<>(le.getId(), le.getDescricao()));
        }
        return res;
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
        // Se um pacote tiver um item na lista, então o pacote é removido
    public void removePecas(List<Integer> ids) {
        for(Integer id: ids)
            removePeca(id, false);
    }


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
        ArrayList<Integer> arrP1d = new ArrayList<>(), arrP1I = new ArrayList<>();
        arrP1d.add(2); arrP1d.add(3);
        Peca p1 = new Peca(1,10.1f,"motor-1.0", "motor", arrP1d, arrP1I);

        ArrayList<Integer> arrP2d = new ArrayList<>(), arrP2I = new ArrayList<>();
        Peca p2 = new Peca(2,12.2f,"volante-2.1", "extra", arrP2d, arrP2I);

        ArrayList<Peca> arrPacote1 = new ArrayList<Peca>();
        arrPacote1.add(p1);
        PacoteDeConfiguracao pacote1 = new PacoteDeConfiguracao(1, 25.2f, "Pacote desportivo", arrPacote1);

        LinhaDeEncomenda le1 = new LinhaDeEncomendaPeca(1, 1, p1);
        LinhaDeEncomenda le2 = new LinhaDeEncomendaPeca(2, 1, p2);
        LinhaDeEncomenda le3 = new LinhaDeEncomendaPacote(3, 1, pacote1);

        ArrayList<LinhaDeEncomenda> arrLsE = new ArrayList<>();
        arrLsE.add(le1); arrLsE.add(le2); arrLsE.add(le3);
        Encomenda enc = new Encomenda(1, arrLsE);
        System.out.println(enc.getFatura());

        System.out.println("Se a le com a peca 1 depende da peca 2: " + le1.dependeDe(p2));
        System.out.println("Se a le com o pacote 1 depende da peca 2: " + le1.dependeDe(p2));


        System.out.println("le1 before update : " + enc.getLinhaEncomenda(1).getQuantidade());
        enc.addPeca(p1, 2);
        System.out.println("le1 after update: " + enc.getLinhaEncomenda(1).getQuantidade());
        enc.removePeca(p1.getId(),false);
        System.out.println("enc after rem of le1 : " + enc.getLinhasDeEncomenda().size());
        enc.addPacote(pacote1);
        System.out.println("enc before update of le3 : " + enc.getLinhaEncomenda(3));
        enc.removePacote(1, false);
        System.out.println("enc after rem of le1 : " + enc.getLinhasDeEncomenda().size());
    }
}
