package Business.Encomenda;

import Business.Stock.Peca;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Encomenda {
    private int id;
    private List<LinhaDeEncomenda> linhasDeEncomenda;
    private String status;
    private String descricao;

    public Encomenda() {
        this.descricao = "";
        this.status = "Valid";
        this.linhasDeEncomenda = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getStatus() {
        return status;
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


    public void removeLinhaEncomenda(Integer id){
        int i;
        for(i = 0; i < linhasDeEncomenda.size() && linhasDeEncomenda.get(i).getId() != id; i++);
        if(i != linhasDeEncomenda.size())
            linhasDeEncomenda.remove(i);
    }

    public void removeLEDependenteDe(int idle, boolean desfazer, PacoteDeConfiguracao p){
        LinhaDeEncomenda le = getLinhaEncomenda(idle);
        if(le != null && le instanceof LinhaDeEncomendaPacote && desfazer){
            LinhaDeEncomendaPacote lep = (LinhaDeEncomendaPacote) le;
            Map<Peca, Integer> pecas = lep.getPacoteDeConfiguracao().getPecas();
            List<Integer> deps = p.getPecasIds();
            for(Peca peca : pecas.keySet()){
                boolean toAdd = true;
                for(int i = 0; i < deps.size() && toAdd; i++)
                    if(peca.getDependencias().contains(Integer.valueOf(deps.get(i))))
                        toAdd = false;
                if(toAdd)
                    addPeca(peca, pecas.get(peca));
            }
        }
        removeLinhaEncomenda(idle);
    }

    public void removeLEIncompativelCom(int idle, boolean desfazer, PacoteDeConfiguracao p){
        LinhaDeEncomenda le = getLinhaEncomenda(idle);
        if(le != null && le instanceof LinhaDeEncomendaPacote && desfazer){
            LinhaDeEncomendaPacote lep = (LinhaDeEncomendaPacote) le;
            Map<Peca, Integer> pecas = lep.getPacoteDeConfiguracao().getPecas();
            List<Integer> deps = p.getPecasIds();
            for(Peca peca : pecas.keySet()){
                boolean toAdd = true;
                for(int i = 0; i < deps.size() && toAdd; i++)
                    if(peca.getIncompatibilidades().contains(Integer.valueOf(deps.get(i)))
                            || p.getIncompatibilidades().contains(Integer.valueOf(p.getId())))
                        toAdd = false;
                if(toAdd)
                    addPeca(peca, pecas.get(peca));
            }
        }
        removeLinhaEncomenda(idle);
    }

    public void removeLEDependenteDe(int idle, boolean desfazer, Peca p){
        LinhaDeEncomenda le = getLinhaEncomenda(idle);
        if(le != null && le instanceof LinhaDeEncomendaPacote && desfazer){
            LinhaDeEncomendaPacote lep = (LinhaDeEncomendaPacote) le;
            Map<Peca, Integer> pecas = lep.getPacoteDeConfiguracao().getPecas();
            for(Peca peca : pecas.keySet())
                if(!peca.getDependencias().contains(Integer.valueOf(p.getId())))
                    addPeca(peca, pecas.get(peca));
        }
        removeLinhaEncomenda(idle);
    }


    public void removeLEIncompativelCom(int idle, boolean desfazer, Peca p){
        LinhaDeEncomenda le = getLinhaEncomenda(idle);
        if(le != null && le instanceof LinhaDeEncomendaPacote && desfazer){
            LinhaDeEncomendaPacote lep = (LinhaDeEncomendaPacote) le;
            Map<Peca, Integer> pecas = lep.getPacoteDeConfiguracao().getPecas();
            for(Peca peca : pecas.keySet())
                if(!peca.getIncompatibilidades().contains(Integer.valueOf(p.getId()))
                    || p.getIncompatibilidades().contains(Integer.valueOf(peca.getId())))
                    addPeca(peca, pecas.get(peca));
        }
        removeLinhaEncomenda(idle);
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
        int width = 40;
        StringBuilder sb = new StringBuilder();
        for(LinhaDeEncomenda le: linhasDeEncomenda){
            int descSize = le.getDescricao().length();
            int quantSize = Integer.toString(le.getQuantidade()).length();
            int precoSize = Float.toString(le.getPrecoTotal()).length();
            int nTraços = width - descSize - quantSize - precoSize - 5;
            sb.append(le.getDescricao() + " ");
            for(int i = 0; i < nTraços; i++) sb.append("-");
            sb.append(" " +  le.getQuantidade() + " - " + le.getPrecoTotal() + "\n");
        }
        sb.append("Preco ----------------------------- " + getPreco() +"\n");
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
        arrP1d.add(2);
        Peca p1 = new Peca(1,10.12f,"motor-1.0", "motor", arrP1d, arrP1I);

        ArrayList<Integer> arrP2d = new ArrayList<>(), arrP2I = new ArrayList<>();
        Peca p2 = new Peca(2,12.20f,"volante-2.1", "extra", arrP2d, arrP2I);

        ArrayList<Integer> arrP3d = new ArrayList<>(), arrP3I = new ArrayList<>();
        Peca p3 = new Peca( 3, 25.20f, "radio", "extras", arrP3d, arrP3I);

        Map<Peca, Integer> arrPacote1 = new HashMap<>();
        arrPacote1.put(p1, 2); arrPacote1.put(p3, 1);
        PacoteDeConfiguracao pacote1 = new PacoteDeConfiguracao(1, 25.20f, "Pacote desportivo", arrPacote1);

        Map<Peca, Integer> arrPacote2 = new HashMap<>();
        arrPacote2.put(p2, 2);
        PacoteDeConfiguracao pacote2 = new PacoteDeConfiguracao(2, 23.20f, "Pacote smooths", arrPacote2);

        LinhaDeEncomenda le1 = new LinhaDeEncomendaPeca(1, 1, p1);
        LinhaDeEncomenda le2 = new LinhaDeEncomendaPeca(2, 1, p2);
        LinhaDeEncomenda le3 = new LinhaDeEncomendaPacote(3, 1, pacote1);
        LinhaDeEncomenda le4 = new LinhaDeEncomendaPacote(4, 1, pacote2);

        ArrayList<LinhaDeEncomenda> arrLsE = new ArrayList<>();
        arrLsE.add(le1); arrLsE.add(le2); arrLsE.add(le3); arrLsE.add(le4);
        Encomenda enc = new Encomenda(1, arrLsE);

        //System.out.println("Se a le com a peca 1 depende da peca 2: " + le1.dependeDe(p2));
        //System.out.println("Se a le com o pacote 1 depende do pacote 2: " + le3.dependeDe(pacote2));

        enc.removeLEDependenteDe(3, true, pacote2);
        System.out.println(enc.getFatura());


    }
}
