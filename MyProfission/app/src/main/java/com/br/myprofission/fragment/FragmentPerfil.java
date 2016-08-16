package com.br.myprofission.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.myprofission.ChatActivity;
import com.br.myprofission.FotoPerfilActivity;
import com.br.myprofission.MainActivity;
import com.br.myprofission.R;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.util.Utilitaria;

import java.io.IOException;
import java.util.List;


/**
 * Created by LENOVO on 22/04/2016.
 */
public class FragmentPerfil extends Fragment {

    private Activity activity;
    private List<Usuario> lista;
    private EditText edt_nome;
    private EditText edt_sobre;
    private EditText edt_tel;
    private EditText edt_email;
    //private EditText edt_profissao;
    private EditText edt_latitude;
    private EditText edt_longitude;
    private EditText edt_endereco;
    private EditText edt_cidade;
    private EditText edt_uf;
    private EditText edt_pais;
   // private TextInputLayout input_nome;
   // private TextInputLayout input_email;
    private AutoCompleteTextView edt_profissao;
  //  private TextInputLayout input_sobre;
  //  private TextInputLayout input_tel;
   // private TextInputLayout input_profissao;
    private static final String[] PROFISSAO = new String[] {
          "Administrador(a)","Administrador(a) Condominial","Administrador(a) de Banco de Dados - DBA","Administrador(a) de Clínica de Estética","Administrador(a) de Redes","Advogado(a)","Agente Administrativo","Agente de Aeroporto","Agente de Comércio Exterior","Agente de Defesa Ambiental","Agente de Marketing","Agente de Negócios","Agente de Portaria","Agente de Retenção","Agente de Segurança","Agente de Trânsito","Agente de Turismo","Almoxarife","Analista Comercial","Analista Contabil","Analista da Qualidade","Analista de Atendimento","Analista de Controladoria","Analista de Crédito","Analista de Custos","Analista de Informação e Adminstração","Analista de Laboratório","Analista de Logística","Analista de Mercado","Analista de O & M","Analista de Orçamento","Analista de PCP","Analista de Pesquisa","Analista de Planejamento","Analista de Projetos","Analista de R H - Recursos Humanos","Analista de Risco de Mercado","Analista de Seguros","Analista de Sistemas","Analista de Suporte","Analista de Treinamento","Analista do Produto","Analista Financeiro","Analista Fiscal","Analista Jurídico","Analista Tributário","Angariador de Seguros","Antropólogo(a)","Apontador(a) de Produção","Apontador(a) de Tráfego","Aposentado","Arquiteto","Arquiteto Urbanista","Arquivista","Arte-finalista","Assessor(a)","Assessor(a) Adm. Financeiro","Assessor(a) Administrativo","Assessor(a) Comercial","Assessor(a) Comunitário","Assessor(a) Contábil","Assessor(a) Cultural","Assessor(a) de Cliente","Assessor(a) de Comunicação","Assessor(a) de Coordenação","Assessor(a) de Marketing","Assessor(a) de Produção","Assessor(a) de Tecnologia","Assessor(a) de Vendas","Assessor(a) Financeiro","Assessor(a) Jurídico","Assistente Adm. Financeiro","Assistente Administrativo","Assistente Comercial","Assistente Contábil","Assistente de A & B - Alimentos e Bebidas","Assistente de Administração Pessoal","Assistente de Atendimento","Assistente de Avaliação","Assistente de Biblioteca","Assistente de Bordo","Assistente de Compras","Assistente de Comunicação","Assistente de Consultoria","Assistente de Controladoria","Assistente de Cpd","Assistente de Crédito e Cobrança","Assistente de Depto. de Pessoal","Assistente de Depto. Fiscal","Assistente de Diretoria","Assistente de Gerência","Assistente de Imp. e Exportação","Assistente de Marketing","Assistente de Mídia","Assistente de PCP","Assistente de Pesquisa","Assistente de Projetos","Assistente de Publicidade e Propaganda","Assistente de Qualidade","Assistente de Rh - Recursos Humanos","Assistente de Tráfego","Assistente de Treinamento","Assistente de Vendas","Assistente do Terceiro Setor","Assistente Executiva Bilíingue","Assistente Financeiro","Assistente Social","Atendente","Atendente de Cadastro","Atendente de Call Center","Atendente de Clientes","Atendente de Crédito","Atendente de Help-Desk","Atendente de Reservas","Atendente Jurídico","Atendente Publicitário","Atuário","Auditor(a)","Auditor(a) da Qualidade","Auxiliar Adm. Financeiro","Auxiliar Administrativo","Auxiliar Comercial","Auxiliar de Almoxarifado","Auxiliar de Área","Auxiliar de Atendimento","Auxiliar de Auditoria","Auxiliar de Cadastro","Auxiliar de Cobrança","Auxiliar de Comércio Exterior","Auxiliar de Compras","Auxiliar de Contabilidade","Auxiliar de Controle de Mercadoria","Auxiliar de Controle de Qualidade","Auxiliar de Coordenação","Auxiliar de Cozinha","Auxiliar de Crédito","Auxiliar de Custos","Auxiliar de Depto. de Pessoal","Auxiliar de Depto. Fiscal","Auxiliar de Despachante","Auxiliar de Enfermagem","Auxiliar de Enfermagem do Trabalho","Auxiliar de Engenharia","Auxiliar de Escritório","Auxiliar de Estilista","Auxiliar de Estoque","Auxiliar de Expedição","Auxiliar de Factoring","Auxiliar de Farmácia","Auxiliar de Faturamento","Auxiliar de Filmagem e Edição","Auxiliar de Fretamento","Auxiliar de Gerência","Auxiliar de Importação","Auxiliar de Informática","Auxiliar de Laboratório de Análises Clínicas","Auxiliar de Loja","Auxiliar de Manutenção","Auxiliar de Mecânico Naval","Auxiliar de O &  M","Auxiliar de Odontologia","Auxiliar de Padaria","Auxiliar de Patrimônio","Auxiliar de Pcp","Auxiliar de Pesquisa","Auxiliar de Portaria","Auxiliar de Processamento","Auxiliar de Produção","Auxiliar de Professor","Auxiliar de R H - Recursos Humanos","Auxiliar de Reservas","Auxiliar de Seção","Auxiliar de Secretaria","Auxiliar de Serrador","Auxiliar de Serviços Gerais","Auxiliar de Serviços Gráficos","Auxiliar de Serviços Notariais","Auxiliar de Supervisão Técnica","Auxiliar de Sushiman","Auxiliar de Tesouraria","Auxiliar de Treinamento","Auxiliar de Vendas","Auxiliar de Xaroparia","Auxiliar Financeiro","Auxiliar Técnico","Balconista","Bancário(a)","Barman","Bibliotecário(a)","Biólogo(a)","Biomédico(a)","Bioquímico(a)","Bolsista","Bombeiro","Boqueteiro(a)","Cabeleleiro(a)","Cabo","Cadastrador(a)","Cadista","Caixa","Caldereiro","Camareiro(a)","Capatazia","Captador(a) de Anúncios","Chefe Administrativo","Chefe de Atendimento","Chefe de Buffet","Chefe de Cobrança","Chefe de Compras","Chefe de Contabilidade","Chefe de Contas a Pagar e Receber","Chefe de Custos","Chefe de Depto. de Pessoal","Chefe de Depto. de Turismo","Chefe de Divisão","Chefe de Engenharia","Chefe de Equipe","Chefe de Escritório","Chefe de Gabinete","Chefe de Informática","Chefe de Logística e Suprimentos","Chefe de Manutenção","Chefe de Produção","Chefe de R H - Recursos Humanos","Chefe de Recepção","Chefe de Rouparia","Chefe de Serviços","Chefe de Setor","Chefe de Vendas","Churrasqueiro(a)","Cientista Político","Cientista Social","Cinegrafista","Cirurgião(ã) Dentista","Cobrador(a)","Comissário(a) de Vôo","Comprador(a)","Comunicador(a) Social","Conciliador(a) Contábil","Confeccionista","Confeiteiro(a)","Conferente","Construtor(a) Imobiliário","Consultor(a)","Consultor(a) Administrativo","Consultor(a) Comercial","Consultor(a) Contábil","Consultor(a) de Implantação","Consultor(a) de Negócio","Consultor(a) de O & M","Consultor(a) de R H - Recursos Humanos","Consultor(a) de Tecnologia da Informação","Consultor(a) de Telecomunicações","Consultor(a) de Vendas","Consultor(a) Financeiro","Consultor(a) Jurídico","Consultor(a) Organizacional","Consultor(a) Técnico","Consultor(a) Tributário","Contador(a)","Contínuo (Office-Boy)","Controlador(a) de Cargas","Controlador(a) de Estações e Pessoal","Controlador(a) de Rotas","Controlador(a) de Tráfego","Controller","Coordenador(a)","Coordenador(a) Adm. Financeiro","Coordenador(a) Administrativo","Coordenador(a) Comercial","Coordenador(a) da Qualidade","Coordenador(a) de Atendimento","Coordenador(a) de Centro de Estágios","Coordenador(a) de Comércio Exterior","Coordenador(a) de Contas Especiais","Coordenador(a) de Crédito Pessoal","Coordenador(a) de Curso de Pós Graduação","Coordenador(a) de Cursos e Serviços","Coordenador(a) de Decoração","Coordenador(a) de Equipe","Coordenador(a) de Eventos","Coordenador(a) de Filial","Coordenador(a) de Imprensa e Divulgação","Coordenador(a) de Informática","Coordenador(a) de Logística","Coordenador(a) de Marketing","Coordenador(a) de Merchandising","Coordenador(a) de Obras","Coordenador(a) de Pessoal","Coordenador(a) de Projetos","Coordenador(a) de R H - Recursos Humanos","Coordenador(a) de Reservas","Coordenador(a) de Segurança","Coordenador(a) de Suporte","Coordenador(a) de Telemarketing","Coordenador(a) de Vendas","Coordenador(a) Financeiro","Coordenador(a) Jurídico","Coordenador(a) Operacional","Coordenador(a) Pedagógico","Coordenador(a) Regional","Copeiro(a)","Copiador(a)","Corretor(a) de Imóveis","Corretor(a) de Previdência Privada","Corretor(a) de Seguros","Corretor(a) de Veículos","Cortador(a) de Carne (Açogueiro)","Costureiro(a)","Cozinheiro(a)","Criação Publicitária","Cronoanalista","Cummin","Datilógrafo(a)","Defensor(a) Público","Demonstrador(a)","Demonstrador(a) de Veículos","Desempregado","Desenhista","Desenhista Autocad","Desenhista Industrial","Designer Gráfico","Diagramador(a) e Editoração Eletrônica","Digitador(a)","Diretor(a)","Diretor(a) Adm. Financeiro","Diretor(a) Administrativo","Diretor(a) Comercial","Diretor(a) de Arte","Diretor(a) de Divisão","Diretor(a) de Escola","Diretor(a) de Logística","Diretor(a) de Marketing e Propaganda","Diretor(a) de Planejamento","Diretor(a) de Provas","Diretor(a) de R H - Recursos Humanos","Diretor(a) de T I - Tecnologia da Informação","Diretor(a) de Vendas","Diretor(a) Executivo","Diretor(a) Financeiro","Diretor(a) Industrial","Diretor(a) Presidente","Diretor(a) Técnico","Diretor(a) Vice-Presidente","Doméstico(a)","Economista","Editor(a) Gráfico","Editor(a) não Linear","Educador(a) Social","Eletricista","Eletricista Automotivo","Eletrotécnico(a)","Emissor(a) de Passagens Áereas","Empacotador(a)","Empresário(a)","Encarregado(a)","Encarregado(a) Adm. Financeiro","Encarregado(a) Administrativo","Encarregado(a) de Arquivo","Encarregado(a) de Atendimento","Encarregado(a) de Cobrança","Encarregado(a) de Compras","Encarregado(a) de Contabilidade","Encarregado(a) de Contas a Pagar e Receber","Encarregado(a) de Controles","Encarregado(a) de Cpd","Encarregado(a) de Custos","Encarregado(a) de Depósito","Encarregado(a) de Distrito","Encarregado(a) de Escritório","Encarregado(a) de Expedição e Tráfego","Encarregado(a) de Faturamento","Encarregado(a) de Logística","Encarregado(a) de Marketing","Encarregado(a) de Patrimônio","Encarregado(a) de PCP","Encarregado(a) de Produção","Encarregado(a) de Recepção de Pescado","Encarregado(a) de Risco e Crédito","Encarregado(a) de Seção","Encarregado(a) de Serviços Administrativos","Encarregado(a) de Setor de Pessoal","Encarregado(a) de Setor Fiscal","Encarregado(a) de Turma","Encarregado(a) Financeiro","Enfermeiro(a)","Engenheiro(a) Agrônomo","Engenheiro(a) Civil","Engenheiro(a) da Qualidade","Engenheiro(a) de Alimentos","Engenheiro(a) de Aplicação","Engenheiro(a) de Áudio","Engenheiro(a) de Automação","Engenheiro(a) de Eletricidade","Engenheiro(a) de Eletrônica","Engenheiro(a) de Pesca","Engenheiro(a) de Petróleo","Engenheiro(a) de Produção","Engenheiro(a) de Produto","Engenheiro(a) de Segurança do Trabalho","Engenheiro(a) de Sistemas","Engenheiro(a) de Telecomunicações","Engenheiro(a) Mecânico","Engenheiro(a) Químico","Entregador(a)","Escrevente","Escriturário(a)","Estagiário(a)","Estagiário(a) em Administração","Estagiário(a) em Bibliotecomomia","Estagiário(a) em Ciências Sociais","Estágiário(a) em Comunicação Social","Estagiário(a) em Contabilidade","Estagiário(a) em Direito","Estagiário(a) em Economia","Estagiário(a) em Economia Doméstica","Estagiário(a) em Edificações","Estagiário(a) em Educação Física","Estagiário(a) em Engenharia Civil","Estagiário(a) em Engenharia de Alimentos","Estagiário(a) em Engenharia de Produção","Estagiário(a) em Engenharia Elétrica","Estagiário(a) em Engenharia Mecânica","Estagiário(a) em Engenharia Química","Estagiário(a) em Estilismo e Moda","Estagiário(a) em Estoque","Estagiário(a) em Fisioterapia","Estagiário(a) em Geografia","Estagiário(a) em Informática","Estagiário(a) em Laboratório","Estágiário(a) em Marketing","Estagiário(a) em Mecânica Industrial","Estagiário(a) em Nutrição","Estagiário(a) em Pedagogia","Estagiário(a) em Psicologia","Estagiário(a) em R H - Recursos Humanos","Estagiário(a) em Segurança do Trabalho","Estagiário(a) em Serviço Social","Estagiário(a) em Turismo","Estagiário(a) Técnico Metalúrgico","Estatístico(a)","Estilista","Estoquista","Estudante Ensino Fundamental","Estudante Ensino Médio","Estudante Ensino Superior","Executivo(a) de Negócios","Extensionista de Nutrição","Facilitador(a) de Grupos","Farmacêutico(a)","Faturista","Fiscal Administrativo","Fiscal de Caixa","Fiscal de Eventos","Fiscal de Loja","Fiscal de Serviços Públicos","Fiscal de Terminal","Fiscal de Tráfego","Fiscal de Trânsito","Fiscal de Tributos","Fiscal de Vendas","Fisioterapêuta","Fonoaudiólogo(a)","Forneiro(a)","Fotógrafo(a)","Frentista","Garantista","Garçom","Geólogo(a)","Geógrafo(a)","Gerente Adm. Financeiro","Gerente Administrativo","Gerente Auxiliar","Gerente Comercial","Gerente Contábil","Gerente de A & B - Alimentos e Bebidas","Gerente de Aquisição","Gerente de Atendimento","Gerente de Auditoria","Gerente de Buffet","Gerente de Caixa","Gerente de Call Center","Gerente de Câmbio","Gerente de Clínica de Estética","Gerente de Cobranças","Gerente de Compras","Gerente de Comunicação","Gerente de Concessionária","Gerente de Consultoria","Gerente de Contas","Gerente de Controladoria","Gerente de Cpd","Gerente de Crédito","Gerente de Depto. Pessoal","Gerente de Desenvolvimento","Gerente de Distribuição e Logística","Gerente de Estatística","Gerente de Estoque","Gerente de Expediente","Gerente de Factoring","Gerente de Filial","Gerente de Hotel","Gerente de Loja","Gerente de Manutenção","Gerente de Marketing","Gerente de Negócios","Gerente de Núcleo","Gerente de Obras","Gerente de Operações","Gerente de Pcp","Gerente de Pista","Gerente de Planejamento","Gerente de Posto","Gerente de Produção","Gerente de Produto","Gerente de Projetos","Gerente de Qualidade","Gerente de R H - Recursos Humanos","Gerente de Recepção e Reservas","Gerente de Restaurante","Gerente de Seção","Gerente de Serviços","Gerente de Suprimentos","Gerente de Telecomunicações","Gerente de TI - Tecnologia da Informação","Gerente de Treinamento","Gerente de Vendas","Gerente Executivo","Gerente Financeiro","Gerente Geral","Gerente Industrial","Gerente Jurídico","Gerente Operacional","Gerente Regional","Gerente Técnico","Gerente Trainee","Gestor Ambiental","Governanta","Guia Turístico","Habilitador de Telefone Celular","Impressor de Off Set","Impressor de Serigrafia","Informante de Spc","Informante de Turismo","Inspetor(a) de Aeronaves","Inspetor(a) de Controle de Qualidade","Inspetor(a) de Embalagem","Inspetor(a) de Produtos de Origem Animal","Inspetor(a) de Segurança","Inspetor(a) de Seguros","Instalador de Cerca Eletrificada","Instalador(a) Rep. de Linhas e Aparelhos (Irla)","Instrutor(a)","Instrutor(a) de Informática","Intérprete","Jornalista","Juiz(a) de Direito","Laboratorista","Laboratorista Fotográfica","Leiloeiro(a)","Lider de Segurança","Locutor(a) de Rádio","Logistica","Maitre","Manobrista","Marceneiro","Massagista","Masseiro(a)","Mecânico(a)","Mecânico(a) Industrial","Mecânico(a) Regulador de Injeção Eletrônica","Mecanógrafo(a)","Médico(a)","Médico(a) do Trabalho","Meio Oficial de Almoxarife","Mestre de Padaria e Confeitaria","Microbiologista","Militar","Missionário(a)","Modelista","Monitor(a)","Monitor(a) de Informatica","Monitor(a) de Produção","Monitor(a) de Telemarketing","Motociclista","Motorista","Motorista Carreteiro","Nutricionista","Odontólogo(a)","Oficial da Reserva","Operador(a) Comercial","Operador(a) de Áudio","Operador(a) de Caixa","Operador(a) de Central de Crédito","Operador(a) de Computador","Operador(a) de Eletro Erosão","Operador(a) de Empilhadeira","Operador(a) de Encaixe e Risco em Cad","Operador(a) de Máquina","Operador(a) de Overend","Operador(a) de Produção","Operador(a) de Telecobrança","Operador(a) de Telecomunicações","Operador(a) de Telemarketing (TMK)","Operador(a) de Televendas","Orientador(a) Acadêmico","Orientador(a) de Pesquisas Educacionais","Orientador(a) Pedagógico","Orientador(a) Profissional","Ouvidor(a)","Padeiro","Paginador(a)","Paisagista","Palestrante","Pastor","Pedagogo(a)","Pedreiro","Perito(a)","Perito(a) em Vistoria Veicular","Personal Trainer","Pesquisador(a)","Piloto de Teste","Pizzaiolo(a)","Piscineiro(a)","Porteiro(a)","Preparador(a) de Documentos","Preposto(a)","Prestador(a) de Serviço","Procurador(a) de Justiça","Professor(a)","Professor(a) de Biologia","Professor(a) de Contabilidade","Professor(a) de Educação Física","Professor(a) de Educação Infantil","Professor(a) de Espanhol","Professor(a) de Física","Professor(a) de Gaita","Professor(a) de Geografia","Professor(a) de Hipnose Clínica","Professor(a) de História","Professor(a) de Informática","Professor(a) de Inglês","Professor(a) de Língua Portuguesa","Professor(a) de Literatura","Professor(a) de Matemática","Professor(a) de Psicologia Organizacional","Professor(a) de Química","Professor(a) de Redação","Professor(a) de Sociologia","Professor(a) Particular","Professor(a) Universitário","Programador(a)","Programador(a) de Pcp","Programador(a) Visual","Promotor(a)","Projetista","Promotor(a) de Eventos","Promotor(a) de Justiça","Promotor(a) de Marketing","Promotor(a) de Merchandising","Promotor(a) de Vendas","Propagandista","Prospector(a) Comercial","Psicólogo(a)","Psicopedagogo(a)","Químico","Recadastrador(a)","Recenseador(a)","Recepcionista","Recepcionista de Anúncios","Recepcionista de Cia. Aérea","Recepcionista de Crediário","Recepcionista de Eventos","Recepcionista Hoteleiro","Recepcionista Orçamentista","Recreador(a) Infantil","Recuperador(a) de Créditos","Redator(a)","Relações Públicas","Relator(a)","Repórter","Repositor(a)","Repositor(a) de Gôndola","Repositor(a) de Horti-frut","Representante Comercial","Representante de Serviços","Revisor(a) Técnica","Salgadeiro(a)","Secretário(a)","Secretário(a) de Administração","Secretário(a) de Administração e Finanças","Secretário(a) de Finanças","Secretário(a) Executivo(a)","Secretário(a) Executivo(a) Bilíngüe","Segurança","Serralheiro(a) de Construção","Serviço de Capatazia","Serviço Voluntário","Serviços Gerais","Síndico(a)","Sócio","Sociólogo(a)","Soldador(a)","Somelier","Sorveteiro(a)","Sub-Comandante","Sub-Contador(a)","Sub-Gerente","Sub-Gerente de Produção","Superintendente","Superintendente de Informática","Supervisor(a)","Supervisor(a) Adm. Financeiro","Supervisor(a) Administrativo","Supervisor(a) Comercial","Supervisor(a) Contábil","Supervisor(a) de Almoxarifado","Supervisor(a) de Atendimento","Supervisor(a) de Call Center","Supervisor(a) de Campo","Supervisor(a) de Controle de Qualidade","Supervisor(a) de Digitação","Supervisor(a) de Ensino","Supervisor(a) de Estágio","Supervisor(a) de Formulação","Supervisor(a) de Informática","Supervisor(a) de Logística","Supervisor(a) de Manutenção Elétrica","Supervisor(a) de Merchandising","Supervisor(a) de Monitoramento (telecom)","Supervisor(a) de Montagem Elétrica","Supervisor(a) de Obras","Supervisor(a) de PCP","Supervisor(a) de Postos","Supervisor(a) de Produção","Supervisor(a) de R H - Recursos Humanos","Supervisor(a) de Riscos","Supervisor(a) de Segurança","Supervisor(a) de Telemarketing","Supervisor(a) de Vendas","Supervisor(a) Financeiro","Supervisor(a) Técnico","Suporte Técnico em Informática","Sushiman","Tabelião(ã)","Técnico(a) Administrativo","Técnico(a) Agricola","Técnico(a) Bibliotecário","Técnico(a) de Natação","Técnico(a) de Nível Superior","Tecnico(a) de Processos","Técnico(a) em Alimentos","Técnico(a) em Arquitetura","Técnico(a) em AutoCad","Técnico(a) em Classificação e Degustação","Técnico(a) em Comércio Exterior","Técnico(a) em Conectividade","Técnico(a) em Contabilidade","Técnico(a) em Copiadoras","Técnico(a) em Curtimento","Técnico(a) em Edificações","Técnico(a) em Eletromecânica","Técnico(a) em Eletrônica de Aviação","Técnico(a) em Embalagem","Técnico(a) em Energia Eólica","Técnico(a) em Enfermagem","Técnico(a) em Estradas","Técnico(a) em Geotecnologia","Técnico(a) em Informática (Manut. Hardware)","Técnico(a) em Informática e Proc. de Dados","Técnico(a) em Laboratório","Técnico(a) em Manutenção","Técnico(a) em Máq. de Escrever e Calculadoras","Técnico(a) em Mecânica Industrial","Técnico(a) em Mineração","Técnico(a) em Nutrição e Dietética","Técnico(a) em Pesquisa","Técnico(a) em Planej. de Manut. Elétrica","Técnico(a) em Processo (Senior)","Técnico(a) em Qualidade","Técnico(a) em Rádio, Som, Vídeo e Televisão","Técnico(a) em Radiologia (Radioterapia)","Técnico(a) em Rede Elétrica","Técnico(a) em Redes de Computadores","Técnico(a) em Refrigeração e Ar Condicionado","Técnico(a) em Segurança do Trabalho","Técnico(a) em Seguros","Técnico(a) em Suprimentos","Técnico(a) em Telecomunicações","Técnico(a) em Turismo","Técnico(a) Têxtil","Telefonista","Telemarketing","Terapeuta Ocupacional","Tesoureiro(a)","Topógrafo","Tornearia em Geral","Traçador","Tradutor(a)","Vendedor(a)","Trainee","Vendedor(a) de Passagens","Vendedor(a) Externo","Vendedor(a) Interno","Vendedor(a) Técnico","Vice-Prefeito(a)","Vigilante","Vistoriador(a)","Voluntário(a)","Web Designer ", "Web Developer","Web Master"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_perfil,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


// In the onCreate method
        final AutoCompleteTextView edt_profissao = (AutoCompleteTextView) activity.findViewById(R.id.edt_profissao);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, PROFISSAO);
        edt_profissao.setAdapter(adapter);
        edt_nome= (EditText) activity.findViewById(R.id.edt_nome);
        edt_sobre= (EditText) activity.findViewById(R.id.edt_sobre);
        edt_tel= (EditText) activity.findViewById(R.id.edt_tel);
        edt_email= (EditText) activity.findViewById(R.id.edt_email);
        //edt_profissao= (EditText) activity.findViewById(R.id.edt_profissao);
        edt_endereco= (EditText) activity.findViewById(R.id.edt_endereco);
        edt_uf= (EditText) activity.findViewById(R.id.edt_uf);
        edt_cidade= (EditText) activity.findViewById(R.id.edt_cidade);
        edt_pais= (EditText) activity.findViewById(R.id.edt_pais);
        //input_nome= (TextInputLayout) activity.findViewById(R.id.input_nome);
        //input_email= (TextInputLayout) activity.findViewById(R.id.input_email);
        //input_profissao= (TextInputLayout) activity.findViewById(R.id.input_profissao);
        //input_tel= (TextInputLayout) activity.findViewById(R.id.input_tel);
        //input_sobre= (TextInputLayout) activity.findViewById(R.id.input_sobre);
        //geolocalizacao
        edt_latitude = (EditText) activity.findViewById(R.id.edt_latitude);
        edt_longitude = (EditText) activity.findViewById(R.id.edt_longitude);
        ImageView img_editar= (ImageView) activity.findViewById(R.id.img_editar);
        ImageView img_perfil= (ImageView) activity.findViewById(R.id.img_perfil);
        Button btn_salvar= (Button) activity.findViewById(R.id.btn_salvar);
        Button btn_localizacao= (Button) activity.findViewById(R.id.btn_localizacao);

        //verificaGPS();//se verifica se gps esta ligado, senão sera ligado
        startGPS();//inici a captura das coordenadas

        /*Button btn_atualiza= (Button) activity.findViewById(R.id.btn_teste);

        btn_atualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ChatActivity.class);
                startActivity(i);
            }
        });
*/
        // Se não possui permissão
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
            } else {
                //se tiver permissao para acessar e criar pastas
            }


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(activity, "OPS!! vc negou a permissao de acesso ao status da internet", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:com.br.myprofission"));
                    startActivity(i);
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},0);
                }
            }
        }*/




        final BDUsuario bd= new BDUsuario(activity);
        lista = bd.buscar();

        for(int i=0;i<lista.size();i++) {//for para preencgimnto dos campos do formulario
            String caminho = Environment.getExternalStorageDirectory() +
                    "/MyProfession/"+Utilitaria.retornaEmail(getContext())+".png";

            if(!Utilitaria.diretorioVazio(caminho)){
                ///seta a foto perfil salva no direotio
                final Bitmap bitmap1 = BitmapFactory.decodeFile(caminho);
                img_perfil.setImageBitmap(bitmap1);
            }

            edt_nome.setText(lista.get(i).getNome());
            edt_sobre.setText(lista.get(i).getSobre());
            edt_tel.setText(lista.get(i).getNumero());
            edt_profissao.setText(lista.get(i).getProfissao());
            edt_email.setText(lista.get(i).getEmail());
            edt_cidade.setText(lista.get(i).getCidade());
            edt_uf.setText(lista.get(i).getUf());
            edt_pais.setText(lista.get(i).getPais());
            edt_endereco.setText(lista.get(i).getLogradouro());
            edt_latitude.setText(""+lista.get(i).getLatitude());
            edt_longitude.setText(""+lista.get(i).getLongitude());
            if(Utilitaria.gpsAtivo(activity)) {//se o gps estiver ativo
                try {
                    Address endereco;
                    if (lista.get(i).getLatitude() != null && lista.get(i).getLatitude() != 0.0 && lista.get(i).getLongitude() != null && lista.get(i).getLongitude() != 0.0) {
                        // Toast.makeText(activity, "pegos da lista", Toast.LENGTH_SHORT).show();
                        endereco = buscaEndereco(lista.get(i).getLatitude(), lista.get(i).getLongitude());
                        edt_cidade.setText(endereco.getLocality());
                        edt_uf.setText(endereco.getAdminArea());
                        edt_pais.setText(endereco.getCountryName());

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bd.fecharConexao();
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(submitForm()) {
                    if(Utilitaria.Conectado(activity)) {
                        BDUsuario b=new BDUsuario(activity);
                        Double longi=0.0;
                        Double lati=0.0;
                        Usuario u = new Usuario();
                        u.setNome(edt_nome.getText().toString());
                        u.setSobre(edt_sobre.getText().toString());
                        u.setProfissao(edt_profissao.getText().toString());
                        u.setEmail(edt_email.getText().toString());
                        u.setNumero(edt_tel.getText().toString());
                        if(edt_longitude.getText().toString()!=null && !edt_longitude.getText().toString().isEmpty()) {
                            longi = Double.parseDouble(edt_longitude.getText().toString());
                            //Log.i("long",""+longi);
                        }
                        if(edt_latitude.getText().toString()!=null && !edt_latitude.getText().toString().isEmpty() ) {
                            lati = Double.parseDouble(edt_latitude.getText().toString());
                            //Log.i("lat",""+lati);
                        }
                        u.setLongitude(longi);
                        u.setLatitude(lati);
                        u.setCidade(edt_cidade.getText().toString());
                        u.setUf(edt_uf.getText().toString());
                        u.setPais(edt_pais.getText().toString());
                        u.setLogradouro(edt_endereco.getText().toString());
                        b.atualizar(u);
                        b.editarServidor(activity, edt_nome.getText().toString(), edt_tel.getText().toString(), edt_email.getText().toString(), edt_sobre.getText().toString(),edt_profissao.getText().toString(),lati,longi,edt_endereco.getText().toString(),edt_cidade.getText().toString(),edt_uf.getText().toString(),edt_pais.getText().toString());
                        b.fecharConexao();
                    }else{
                        Toast.makeText(activity, "Operação não pode ser realizada\nVocê não esta conectado a Internet", Toast.LENGTH_SHORT).show();
                    }
               // }

            }
        });
        btn_localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaGPS();
            }
        });
        img_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, FotoPerfilActivity.class);
                startActivity(i);
            }
        });


    }
    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS() {
        LocationManager lManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                updateView(locat);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
    }


    //  Método que faz a atualização da tela para o usuário.
    public void updateView(Location locat){
        Double latitude = locat.getLatitude();
        Double longitude = locat.getLongitude();

        edt_latitude.setText(latitude.toString());
        edt_longitude.setText(longitude.toString());
    }
    public Address buscaEndereco(double latitude ,double longitude)throws IOException{

        Geocoder geocoder;
        Address address=null;
        List<Address> addresses;

        geocoder=new Geocoder(getActivity());
        addresses=geocoder.getFromLocation(latitude,longitude,1);
        if(addresses.size()>0){
            address=addresses.get(0);
        }
        return address;
    }
    public void  verificaGPS(){
        AlertDialog alerta;
        //verifica se gps está destivado
        LocationManager manager = (LocationManager) activity.getSystemService( Context.LOCATION_SERVICE );
        boolean isOn = manager.isProviderEnabled( LocationManager.GPS_PROVIDER);

        if(!isOn)
        {
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            //define o titulo
            builder.setTitle("GPS desativado!");
            //define a mensagem
            builder.setMessage("Deseja ativa-lo?");
            //define um botão como positivo
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();

        }else{
            startGPS();
            recuperaLocalizacao();
        }
    }
    private void recuperaLocalizacao(){
        try {
            Address endereco;
            if(!edt_latitude.getText().toString().isEmpty() && !edt_longitude.getText().toString().isEmpty()){
                endereco = buscaEndereco(Double.parseDouble(edt_latitude.getText().toString()), Double.parseDouble(edt_longitude.getText().toString()));
                edt_cidade.setText(endereco.getLocality());
                edt_uf.setText(endereco.getAdminArea());
                edt_pais.setText(endereco.getCountryName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

