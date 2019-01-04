package Business.Encomenda;

import Business.Stock.Peca;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Encomenda {
    private int id;
    private List<LinhaDeEncomenda> linhasDeEncomenda;
    private String status;
    private String descricao;
   
    public Encomenda() {
        this.descricao = "made by a client" + LocalDateTime.now();
        this.status = "Em espera";
        this.linhasDeEncomenda = new ArrayList<>();
    }

    public Encomenda clone() {
        Encomenda enc = new Encomenda();
        enc.setId(this.getId());
        enc.setStatus(this.getStatus());
        enc.setDescricao(this.getDescricao());
        enc.setLinhasDeEncomenda(this.getLinhasDeEncomenda());
        return enc;
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
        for(LinhaDeEncomenda le: linhasDeEncomenda){
            boolean incompativel = false;
            if(le instanceof LinhaDeEncomendaPeca)
                if(((LinhaDeEncomendaPeca) le).getPeca().getIncompatibilidades().contains(Integer.valueOf(p.getId())))
                    incompativel = true;

            if(le instanceof LinhaDeEncomendaPacote){
                if(((LinhaDeEncomendaPacote) le).getPacoteDeConfiguracao().getIncompatibilidades().contains(Integer.valueOf(p.getId())))
                    incompativel = true;
            }

            if(incompativel)
                res.add(new Pair<>(le.getId(), le.getDescricao()));
            else
                for(Integer inc : incom){
                        if(le.hasPeca(inc) || incompativel)
                            res.add(new Pair<>(le.getId(), le.getDescricao()));
                }
        }
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

    public void removePeca(int id, int quantia) {
        int i;
        for(i = 0; i < linhasDeEncomenda.size(); i++) {
            if(linhasDeEncomenda.get(i) instanceof LinhaDeEncomendaPeca && linhasDeEncomenda.get(i).hasPeca(id))
                break;
        }
        if(i != linhasDeEncomenda.size()){
            LinhaDeEncomendaPeca lep = (LinhaDeEncomendaPeca) linhasDeEncomenda.get(i);
            if(lep.getQuantidade() - quantia > 0)
                lep.setQuantidade(lep.getQuantidade() - quantia);
            else this.linhasDeEncomenda.remove(i);
        }

    }

    public void removePacote(int id, int quantia) {
        int i;
        LinhaDeEncomendaPacote lep = null;
        for(i = 0; i < linhasDeEncomenda.size(); i++) {
            if(linhasDeEncomenda.get(i) instanceof LinhaDeEncomendaPacote){
                lep = (LinhaDeEncomendaPacote) linhasDeEncomenda.get(i);
                if(lep.getPacoteDeConfiguracao().getId() == id)
                    break;
            }
        }
        if(i != linhasDeEncomenda.size())
            if(lep.getQuantidade() - quantia > 0)
                lep.setQuantidade(lep.getQuantidade() - quantia);
            else this.linhasDeEncomenda.remove(i);
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
                    if(peca.dependeDe(deps.get(i)))
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
                    if(peca.incompativelCom(deps.get(i)) || p.incompativelCom(peca))
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
                if(!peca.dependeDe(p))
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
                if(!peca.incompativelCom(p) || p.incompativelCom(peca))
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

    public List<Integer> getPecasObrigatorias(Peca p){
        List<Integer> dep = p.getDependencias();
        for(LinhaDeEncomenda le : linhasDeEncomenda)
            for(int i = 0; i < dep.size(); i++){
                if(le.hasPeca(dep.get(i))){
                    dep.remove(i);
                }
            }
        return dep;
    }


    public boolean canCreatePacote(PacoteDeConfiguracao pacote){
        List<LinhaDeEncomenda> leps = linhasDeEncomenda.stream()
                                    .filter(l -> l instanceof LinhaDeEncomendaPeca)
                                    .collect(Collectors.toList());
        Map<Peca, Integer> pecasDoPacote = pacote.getPecas();
        for(LinhaDeEncomenda l : leps){
            int idPeca = ((LinhaDeEncomendaPeca) l).getIdPeca();
            int quantidade = l.getQuantidade();
            int quantidadeNoPacote = Integer.MAX_VALUE;
            Peca peca = null;
            for(Peca p : pecasDoPacote.keySet()){
                if(p.getId() == idPeca){
                    quantidadeNoPacote = pecasDoPacote.get(p);
                    peca = p;
                }
            }
            if( quantidade >= quantidadeNoPacote && pacote.hasPeca(idPeca))
                pecasDoPacote.remove(peca);
        }
        if(pecasDoPacote.size() == 0)
            return true;
        return false;
    }

    public void createPacote(PacoteDeConfiguracao pacote){
        List<LinhaDeEncomenda> leps = linhasDeEncomenda.stream()
                .filter(l -> l instanceof LinhaDeEncomendaPeca)
                .collect(Collectors.toList());
        Map<Peca, Integer> pecasDoPacote = pacote.getPecas();
        for(Peca p : pecasDoPacote.keySet())
            removePeca(p.getId(), pecasDoPacote.get(p));
        addPacote(pacote);
    }

    public boolean hasCategoriaFilled(String categoria){
        for(LinhaDeEncomenda le: linhasDeEncomenda)
            if(le.getCategorias().contains(categoria))
                return true;
        return false;
    }


    public boolean valid(List<String> categorias) throws Exception{
        for(LinhaDeEncomenda le: linhasDeEncomenda){
            if(le instanceof LinhaDeEncomendaPacote){
                System.out.println(le.getCategorias());
                for(String categoria : le.getCategorias())
                    System.out.println("Categoria Pacote : " + categoria);

            }
            for(String categoria : le.getCategorias())
                if(categorias.contains(categoria))
                    categorias.remove(categoria);
        }
        if(!categorias.isEmpty()){
            String cate = "";
            for(String s : categorias) cate += s+"; ";
            throw new Exception("Categorias que falta serem preenchidas: " + cate);
        }
        return true;
    }

    private void cascadeRemoveLsEDependentesDe(Integer peca){
        Peca p = new Peca();
        p.setId(peca);
        List<Pair<Integer, String>> les = getLsEDependentes(p);
        for(Pair<Integer,String> i : les)
            linhasDeEncomenda.remove(i);
        for(Pair<Integer,String> i : les){
            LinhaDeEncomendaPeca lep = (LinhaDeEncomendaPeca) getLinhaEncomenda(i.getKey());
            if(lep.getIdPeca() != peca)
                cascadeRemoveLsEDependentesDe(lep.getIdPeca());
        }
    }

    public void removeCategoria(String categoria){
        for(int i = 0; i < linhasDeEncomenda.size(); i++){
            LinhaDeEncomenda le = linhasDeEncomenda.get(i);
            if(linhasDeEncomenda.get(i).hasCategoria(categoria)){
                if(le instanceof LinhaDeEncomendaPeca){
                    cascadeRemoveLsEDependentesDe(((LinhaDeEncomendaPeca) le).getIdPeca());
                }
                linhasDeEncomenda.remove(i);
            }
        }
    }

    public boolean NoDepsAndNoInc(){
        for(LinhaDeEncomenda l: linhasDeEncomenda){
            if( l instanceof LinhaDeEncomendaPeca){
                Peca p = ((LinhaDeEncomendaPeca) l).getPeca();
                if(!getLEIncompativeisCom(p).isEmpty()) return false;
                if(!getPecasObrigatorias(p).isEmpty()) return false;
            }
            if( l instanceof  LinhaDeEncomendaPacote){
                Map<Peca, Integer> pecas = ((LinhaDeEncomendaPacote) l).getPacoteDeConfiguracao().getPecas();
                for(Peca p: pecas.keySet()){
                    if(!getLEIncompativeisCom(p).isEmpty()) return false;
                    if(!getPecasObrigatorias(p).isEmpty()) return false;
                }
            }
        }
        return true;
    }
}
