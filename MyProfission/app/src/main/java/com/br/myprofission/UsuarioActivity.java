package com.br.myprofission;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.util.Utilitaria;

public class UsuarioActivity extends AppCompatActivity {
    private BDUsuario bd;
    private TextView passos;
    private EditText numero;
    private EditText nome;
    //private EditText profissao;
    private EditText email;
    private EditText senha;
    private Button btn_prox;
    private Button btn_salvar;
    private Button btn_ja_cadastrado;
    private TextView txt_msg;
    private TextInputLayout input_nome;
    private TextInputLayout input_email;
    private TextInputLayout input_tel;
    private TextInputLayout input_senha;
    private AutoCompleteTextView profissao;
    private static final String[] PROFISSAO = new String[] {
            "Administrador(a)","Administrador(a) Condominial","Administrador(a) de Banco de Dados - DBA","Administrador(a) de Clínica de Estética","Administrador(a) de Redes","Advogado(a)","Agente Administrativo","Agente de Aeroporto","Agente de Comércio Exterior","Agente de Defesa Ambiental","Agente de Marketing","Agente de Negócios","Agente de Portaria","Agente de Retenção","Agente de Segurança","Agente de Trânsito","Agente de Turismo","Almoxarife","Analista Comercial","Analista Contabil","Analista da Qualidade","Analista de Atendimento","Analista de Controladoria","Analista de Crédito","Analista de Custos","Analista de Informação e Adminstração","Analista de Laboratório","Analista de Logística","Analista de Mercado","Analista de O & M","Analista de Orçamento","Analista de PCP","Analista de Pesquisa","Analista de Planejamento","Analista de Projetos","Analista de R H - Recursos Humanos","Analista de Risco de Mercado","Analista de Seguros","Analista de Sistemas","Analista de Suporte","Analista de Treinamento","Analista do Produto","Analista Financeiro","Analista Fiscal","Analista Jurídico","Analista Tributário","Angariador de Seguros","Antropólogo(a)","Apontador(a) de Produção","Apontador(a) de Tráfego","Aposentado","Arquiteto","Arquiteto Urbanista","Arquivista","Arte-finalista","Assessor(a)","Assessor(a) Adm. Financeiro","Assessor(a) Administrativo","Assessor(a) Comercial","Assessor(a) Comunitário","Assessor(a) Contábil","Assessor(a) Cultural","Assessor(a) de Cliente","Assessor(a) de Comunicação","Assessor(a) de Coordenação","Assessor(a) de Marketing","Assessor(a) de Produção","Assessor(a) de Tecnologia","Assessor(a) de Vendas","Assessor(a) Financeiro","Assessor(a) Jurídico","Assistente Adm. Financeiro","Assistente Administrativo","Assistente Comercial","Assistente Contábil","Assistente de A & B - Alimentos e Bebidas","Assistente de Administração Pessoal","Assistente de Atendimento","Assistente de Avaliação","Assistente de Biblioteca","Assistente de Bordo","Assistente de Compras","Assistente de Comunicação","Assistente de Consultoria","Assistente de Controladoria","Assistente de Cpd","Assistente de Crédito e Cobrança","Assistente de Depto. de Pessoal","Assistente de Depto. Fiscal","Assistente de Diretoria","Assistente de Gerência","Assistente de Imp. e Exportação","Assistente de Marketing","Assistente de Mídia","Assistente de PCP","Assistente de Pesquisa","Assistente de Projetos","Assistente de Publicidade e Propaganda","Assistente de Qualidade","Assistente de Rh - Recursos Humanos","Assistente de Tráfego","Assistente de Treinamento","Assistente de Vendas","Assistente do Terceiro Setor","Assistente Executiva Bilíingue","Assistente Financeiro","Assistente Social","Atendente","Atendente de Cadastro","Atendente de Call Center","Atendente de Clientes","Atendente de Crédito","Atendente de Help-Desk","Atendente de Reservas","Atendente Jurídico","Atendente Publicitário","Atuário","Auditor(a)","Auditor(a) da Qualidade","Auxiliar Adm. Financeiro","Auxiliar Administrativo","Auxiliar Comercial","Auxiliar de Almoxarifado","Auxiliar de Área","Auxiliar de Atendimento","Auxiliar de Auditoria","Auxiliar de Cadastro","Auxiliar de Cobrança","Auxiliar de Comércio Exterior","Auxiliar de Compras","Auxiliar de Contabilidade","Auxiliar de Controle de Mercadoria","Auxiliar de Controle de Qualidade","Auxiliar de Coordenação","Auxiliar de Cozinha","Auxiliar de Crédito","Auxiliar de Custos","Auxiliar de Depto. de Pessoal","Auxiliar de Depto. Fiscal","Auxiliar de Despachante","Auxiliar de Enfermagem","Auxiliar de Enfermagem do Trabalho","Auxiliar de Engenharia","Auxiliar de Escritório","Auxiliar de Estilista","Auxiliar de Estoque","Auxiliar de Expedição","Auxiliar de Factoring","Auxiliar de Farmácia","Auxiliar de Faturamento","Auxiliar de Filmagem e Edição","Auxiliar de Fretamento","Auxiliar de Gerência","Auxiliar de Importação","Auxiliar de Informática","Auxiliar de Laboratório de Análises Clínicas","Auxiliar de Loja","Auxiliar de Manutenção","Auxiliar de Mecânico Naval","Auxiliar de O &  M","Auxiliar de Odontologia","Auxiliar de Padaria","Auxiliar de Patrimônio","Auxiliar de Pcp","Auxiliar de Pesquisa","Auxiliar de Portaria","Auxiliar de Processamento","Auxiliar de Produção","Auxiliar de Professor","Auxiliar de R H - Recursos Humanos","Auxiliar de Reservas","Auxiliar de Seção","Auxiliar de Secretaria","Auxiliar de Serrador","Auxiliar de Serviços Gerais","Auxiliar de Serviços Gráficos","Auxiliar de Serviços Notariais","Auxiliar de Supervisão Técnica","Auxiliar de Sushiman","Auxiliar de Tesouraria","Auxiliar de Treinamento","Auxiliar de Vendas","Auxiliar de Xaroparia","Auxiliar Financeiro","Auxiliar Técnico","Balconista","Bancário(a)","Barman","Bibliotecário(a)","Biólogo(a)","Biomédico(a)","Bioquímico(a)","Bolsista","Bombeiro","Boqueteiro(a)","Cabeleleiro(a)","Cabo","Cadastrador(a)","Cadista","Caixa","Caldereiro","Camareiro(a)","Capatazia","Captador(a) de Anúncios","Chefe Administrativo","Chefe de Atendimento","Chefe de Buffet","Chefe de Cobrança","Chefe de Compras","Chefe de Contabilidade","Chefe de Contas a Pagar e Receber","Chefe de Custos","Chefe de Depto. de Pessoal","Chefe de Depto. de Turismo","Chefe de Divisão","Chefe de Engenharia","Chefe de Equipe","Chefe de Escritório","Chefe de Gabinete","Chefe de Informática","Chefe de Logística e Suprimentos","Chefe de Manutenção","Chefe de Produção","Chefe de R H - Recursos Humanos","Chefe de Recepção","Chefe de Rouparia","Chefe de Serviços","Chefe de Setor","Chefe de Vendas","Churrasqueiro(a)","Cientista Político","Cientista Social","Cinegrafista","Cirurgião(ã) Dentista","Cobrador(a)","Comissário(a) de Vôo","Comprador(a)","Comunicador(a) Social","Conciliador(a) Contábil","Confeccionista","Confeiteiro(a)","Conferente","Construtor(a) Imobiliário","Consultor(a)","Consultor(a) Administrativo","Consultor(a) Comercial","Consultor(a) Contábil","Consultor(a) de Implantação","Consultor(a) de Negócio","Consultor(a) de O & M","Consultor(a) de R H - Recursos Humanos","Consultor(a) de Tecnologia da Informação","Consultor(a) de Telecomunicações","Consultor(a) de Vendas","Consultor(a) Financeiro","Consultor(a) Jurídico","Consultor(a) Organizacional","Consultor(a) Técnico","Consultor(a) Tributário","Contador(a)","Contínuo (Office-Boy)","Controlador(a) de Cargas","Controlador(a) de Estações e Pessoal","Controlador(a) de Rotas","Controlador(a) de Tráfego","Controller","Coordenador(a)","Coordenador(a) Adm. Financeiro","Coordenador(a) Administrativo","Coordenador(a) Comercial","Coordenador(a) da Qualidade","Coordenador(a) de Atendimento","Coordenador(a) de Centro de Estágios","Coordenador(a) de Comércio Exterior","Coordenador(a) de Contas Especiais","Coordenador(a) de Crédito Pessoal","Coordenador(a) de Curso de Pós Graduação","Coordenador(a) de Cursos e Serviços","Coordenador(a) de Decoração","Coordenador(a) de Equipe","Coordenador(a) de Eventos","Coordenador(a) de Filial","Coordenador(a) de Imprensa e Divulgação","Coordenador(a) de Informática","Coordenador(a) de Logística","Coordenador(a) de Marketing","Coordenador(a) de Merchandising","Coordenador(a) de Obras","Coordenador(a) de Pessoal","Coordenador(a) de Projetos","Coordenador(a) de R H - Recursos Humanos","Coordenador(a) de Reservas","Coordenador(a) de Segurança","Coordenador(a) de Suporte","Coordenador(a) de Telemarketing","Coordenador(a) de Vendas","Coordenador(a) Financeiro","Coordenador(a) Jurídico","Coordenador(a) Operacional","Coordenador(a) Pedagógico","Coordenador(a) Regional","Copeiro(a)","Copiador(a)","Corretor(a) de Imóveis","Corretor(a) de Previdência Privada","Corretor(a) de Seguros","Corretor(a) de Veículos","Cortador(a) de Carne (Açogueiro)","Costureiro(a)","Cozinheiro(a)","Criação Publicitária","Cronoanalista","Cummin","Datilógrafo(a)","Defensor(a) Público","Demonstrador(a)","Demonstrador(a) de Veículos","Desempregado","Desenhista","Desenhista Autocad","Desenhista Industrial","Designer Gráfico","Diagramador(a) e Editoração Eletrônica","Digitador(a)","Diretor(a)","Diretor(a) Adm. Financeiro","Diretor(a) Administrativo","Diretor(a) Comercial","Diretor(a) de Arte","Diretor(a) de Divisão","Diretor(a) de Escola","Diretor(a) de Logística","Diretor(a) de Marketing e Propaganda","Diretor(a) de Planejamento","Diretor(a) de Provas","Diretor(a) de R H - Recursos Humanos","Diretor(a) de T I - Tecnologia da Informação","Diretor(a) de Vendas","Diretor(a) Executivo","Diretor(a) Financeiro","Diretor(a) Industrial","Diretor(a) Presidente","Diretor(a) Técnico","Diretor(a) Vice-Presidente","Doméstico(a)","Economista","Editor(a) Gráfico","Editor(a) não Linear","Educador(a) Social","Eletricista","Eletricista Automotivo","Eletrotécnico(a)","Emissor(a) de Passagens Áereas","Empacotador(a)","Empresário(a)","Encarregado(a)","Encarregado(a) Adm. Financeiro","Encarregado(a) Administrativo","Encarregado(a) de Arquivo","Encarregado(a) de Atendimento","Encarregado(a) de Cobrança","Encarregado(a) de Compras","Encarregado(a) de Contabilidade","Encarregado(a) de Contas a Pagar e Receber","Encarregado(a) de Controles","Encarregado(a) de Cpd","Encarregado(a) de Custos","Encarregado(a) de Depósito","Encarregado(a) de Distrito","Encarregado(a) de Escritório","Encarregado(a) de Expedição e Tráfego","Encarregado(a) de Faturamento","Encarregado(a) de Logística","Encarregado(a) de Marketing","Encarregado(a) de Patrimônio","Encarregado(a) de PCP","Encarregado(a) de Produção","Encarregado(a) de Recepção de Pescado","Encarregado(a) de Risco e Crédito","Encarregado(a) de Seção","Encarregado(a) de Serviços Administrativos","Encarregado(a) de Setor de Pessoal","Encarregado(a) de Setor Fiscal","Encarregado(a) de Turma","Encarregado(a) Financeiro","Enfermeiro(a)","Engenheiro(a) Agrônomo","Engenheiro(a) Civil","Engenheiro(a) da Qualidade","Engenheiro(a) de Alimentos","Engenheiro(a) de Aplicação","Engenheiro(a) de Áudio","Engenheiro(a) de Automação","Engenheiro(a) de Eletricidade","Engenheiro(a) de Eletrônica","Engenheiro(a) de Pesca","Engenheiro(a) de Petróleo","Engenheiro(a) de Produção","Engenheiro(a) de Produto","Engenheiro(a) de Segurança do Trabalho","Engenheiro(a) de Sistemas","Engenheiro(a) de Telecomunicações","Engenheiro(a) Mecânico","Engenheiro(a) Químico","Entregador(a)","Escrevente","Escriturário(a)","Estagiário(a)","Estagiário(a) em Administração","Estagiário(a) em Bibliotecomomia","Estagiário(a) em Ciências Sociais","Estágiário(a) em Comunicação Social","Estagiário(a) em Contabilidade","Estagiário(a) em Direito","Estagiário(a) em Economia","Estagiário(a) em Economia Doméstica","Estagiário(a) em Edificações","Estagiário(a) em Educação Física","Estagiário(a) em Engenharia Civil","Estagiário(a) em Engenharia de Alimentos","Estagiário(a) em Engenharia de Produção","Estagiário(a) em Engenharia Elétrica","Estagiário(a) em Engenharia Mecânica","Estagiário(a) em Engenharia Química","Estagiário(a) em Estilismo e Moda","Estagiário(a) em Estoque","Estagiário(a) em Fisioterapia","Estagiário(a) em Geografia","Estagiário(a) em Informática","Estagiário(a) em Laboratório","Estágiário(a) em Marketing","Estagiário(a) em Mecânica Industrial","Estagiário(a) em Nutrição","Estagiário(a) em Pedagogia","Estagiário(a) em Psicologia","Estagiário(a) em R H - Recursos Humanos","Estagiário(a) em Segurança do Trabalho","Estagiário(a) em Serviço Social","Estagiário(a) em Turismo","Estagiário(a) Técnico Metalúrgico","Estatístico(a)","Estilista","Estoquista","Estudante Ensino Fundamental","Estudante Ensino Médio","Estudante Ensino Superior","Executivo(a) de Negócios","Extensionista de Nutrição","Facilitador(a) de Grupos","Farmacêutico(a)","Faturista","Fiscal Administrativo","Fiscal de Caixa","Fiscal de Eventos","Fiscal de Loja","Fiscal de Serviços Públicos","Fiscal de Terminal","Fiscal de Tráfego","Fiscal de Trânsito","Fiscal de Tributos","Fiscal de Vendas","Fisioterapêuta","Fonoaudiólogo(a)","Forneiro(a)","Fotógrafo(a)","Frentista","Garantista","Garçom","Geólogo(a)","Geógrafo(a)","Gerente Adm. Financeiro","Gerente Administrativo","Gerente Auxiliar","Gerente Comercial","Gerente Contábil","Gerente de A & B - Alimentos e Bebidas","Gerente de Aquisição","Gerente de Atendimento","Gerente de Auditoria","Gerente de Buffet","Gerente de Caixa","Gerente de Call Center","Gerente de Câmbio","Gerente de Clínica de Estética","Gerente de Cobranças","Gerente de Compras","Gerente de Comunicação","Gerente de Concessionária","Gerente de Consultoria","Gerente de Contas","Gerente de Controladoria","Gerente de Cpd","Gerente de Crédito","Gerente de Depto. Pessoal","Gerente de Desenvolvimento","Gerente de Distribuição e Logística","Gerente de Estatística","Gerente de Estoque","Gerente de Expediente","Gerente de Factoring","Gerente de Filial","Gerente de Hotel","Gerente de Loja","Gerente de Manutenção","Gerente de Marketing","Gerente de Negócios","Gerente de Núcleo","Gerente de Obras","Gerente de Operações","Gerente de Pcp","Gerente de Pista","Gerente de Planejamento","Gerente de Posto","Gerente de Produção","Gerente de Produto","Gerente de Projetos","Gerente de Qualidade","Gerente de R H - Recursos Humanos","Gerente de Recepção e Reservas","Gerente de Restaurante","Gerente de Seção","Gerente de Serviços","Gerente de Suprimentos","Gerente de Telecomunicações","Gerente de TI - Tecnologia da Informação","Gerente de Treinamento","Gerente de Vendas","Gerente Executivo","Gerente Financeiro","Gerente Geral","Gerente Industrial","Gerente Jurídico","Gerente Operacional","Gerente Regional","Gerente Técnico","Gerente Trainee","Gestor Ambiental","Governanta","Guia Turístico","Habilitador de Telefone Celular","Impressor de Off Set","Impressor de Serigrafia","Informante de Spc","Informante de Turismo","Inspetor(a) de Aeronaves","Inspetor(a) de Controle de Qualidade","Inspetor(a) de Embalagem","Inspetor(a) de Produtos de Origem Animal","Inspetor(a) de Segurança","Inspetor(a) de Seguros","Instalador de Cerca Eletrificada","Instalador(a) Rep. de Linhas e Aparelhos (Irla)","Instrutor(a)","Instrutor(a) de Informática","Intérprete","Jornalista","Juiz(a) de Direito","Laboratorista","Laboratorista Fotográfica","Leiloeiro(a)","Lider de Segurança","Locutor(a) de Rádio","Logistica","Maitre","Manobrista","Marceneiro","Massagista","Masseiro(a)","Mecânico(a)","Mecânico(a) Industrial","Mecânico(a) Regulador de Injeção Eletrônica","Mecanógrafo(a)","Médico(a)","Médico(a) do Trabalho","Meio Oficial de Almoxarife","Mestre de Padaria e Confeitaria","Microbiologista","Militar","Missionário(a)","Modelista","Monitor(a)","Monitor(a) de Informatica","Monitor(a) de Produção","Monitor(a) de Telemarketing","Motociclista","Motorista","Motorista Carreteiro","Nutricionista","Odontólogo(a)","Oficial da Reserva","Operador(a) Comercial","Operador(a) de Áudio","Operador(a) de Caixa","Operador(a) de Central de Crédito","Operador(a) de Computador","Operador(a) de Eletro Erosão","Operador(a) de Empilhadeira","Operador(a) de Encaixe e Risco em Cad","Operador(a) de Máquina","Operador(a) de Overend","Operador(a) de Produção","Operador(a) de Telecobrança","Operador(a) de Telecomunicações","Operador(a) de Telemarketing (TMK)","Operador(a) de Televendas","Orientador(a) Acadêmico","Orientador(a) de Pesquisas Educacionais","Orientador(a) Pedagógico","Orientador(a) Profissional","Ouvidor(a)","Padeiro","Paginador(a)","Paisagista","Palestrante","Pastor","Pedagogo(a)","Pedreiro","Perito(a)","Perito(a) em Vistoria Veicular","Personal Trainer","Pesquisador(a)","Piloto de Teste","Pizzaiolo(a)","Piscineiro(a)","Porteiro(a)","Preparador(a) de Documentos","Preposto(a)","Prestador(a) de Serviço","Procurador(a) de Justiça","Professor(a)","Professor(a) de Biologia","Professor(a) de Contabilidade","Professor(a) de Educação Física","Professor(a) de Educação Infantil","Professor(a) de Espanhol","Professor(a) de Física","Professor(a) de Gaita","Professor(a) de Geografia","Professor(a) de Hipnose Clínica","Professor(a) de História","Professor(a) de Informática","Professor(a) de Inglês","Professor(a) de Língua Portuguesa","Professor(a) de Literatura","Professor(a) de Matemática","Professor(a) de Psicologia Organizacional","Professor(a) de Química","Professor(a) de Redação","Professor(a) de Sociologia","Professor(a) Particular","Professor(a) Universitário","Programador(a)","Programador(a) de Pcp","Programador(a) Visual","Promotor(a)","Projetista","Promotor(a) de Eventos","Promotor(a) de Justiça","Promotor(a) de Marketing","Promotor(a) de Merchandising","Promotor(a) de Vendas","Propagandista","Prospector(a) Comercial","Psicólogo(a)","Psicopedagogo(a)","Químico","Recadastrador(a)","Recenseador(a)","Recepcionista","Recepcionista de Anúncios","Recepcionista de Cia. Aérea","Recepcionista de Crediário","Recepcionista de Eventos","Recepcionista Hoteleiro","Recepcionista Orçamentista","Recreador(a) Infantil","Recuperador(a) de Créditos","Redator(a)","Relações Públicas","Relator(a)","Repórter","Repositor(a)","Repositor(a) de Gôndola","Repositor(a) de Horti-frut","Representante Comercial","Representante de Serviços","Revisor(a) Técnica","Salgadeiro(a)","Secretário(a)","Secretário(a) de Administração","Secretário(a) de Administração e Finanças","Secretário(a) de Finanças","Secretário(a) Executivo(a)","Secretário(a) Executivo(a) Bilíngüe","Segurança","Serralheiro(a) de Construção","Serviço de Capatazia","Serviço Voluntário","Serviços Gerais","Síndico(a)","Sócio","Sociólogo(a)","Soldador(a)","Somelier","Sorveteiro(a)","Sub-Comandante","Sub-Contador(a)","Sub-Gerente","Sub-Gerente de Produção","Superintendente","Superintendente de Informática","Supervisor(a)","Supervisor(a) Adm. Financeiro","Supervisor(a) Administrativo","Supervisor(a) Comercial","Supervisor(a) Contábil","Supervisor(a) de Almoxarifado","Supervisor(a) de Atendimento","Supervisor(a) de Call Center","Supervisor(a) de Campo","Supervisor(a) de Controle de Qualidade","Supervisor(a) de Digitação","Supervisor(a) de Ensino","Supervisor(a) de Estágio","Supervisor(a) de Formulação","Supervisor(a) de Informática","Supervisor(a) de Logística","Supervisor(a) de Manutenção Elétrica","Supervisor(a) de Merchandising","Supervisor(a) de Monitoramento (telecom)","Supervisor(a) de Montagem Elétrica","Supervisor(a) de Obras","Supervisor(a) de PCP","Supervisor(a) de Postos","Supervisor(a) de Produção","Supervisor(a) de R H - Recursos Humanos","Supervisor(a) de Riscos","Supervisor(a) de Segurança","Supervisor(a) de Telemarketing","Supervisor(a) de Vendas","Supervisor(a) Financeiro","Supervisor(a) Técnico","Suporte Técnico em Informática","Sushiman","Tabelião(ã)","Técnico(a) Administrativo","Técnico(a) Agricola","Técnico(a) Bibliotecário","Técnico(a) de Natação","Técnico(a) de Nível Superior","Tecnico(a) de Processos","Técnico(a) em Alimentos","Técnico(a) em Arquitetura","Técnico(a) em AutoCad","Técnico(a) em Classificação e Degustação","Técnico(a) em Comércio Exterior","Técnico(a) em Conectividade","Técnico(a) em Contabilidade","Técnico(a) em Copiadoras","Técnico(a) em Curtimento","Técnico(a) em Edificações","Técnico(a) em Eletromecânica","Técnico(a) em Eletrônica de Aviação","Técnico(a) em Embalagem","Técnico(a) em Energia Eólica","Técnico(a) em Enfermagem","Técnico(a) em Estradas","Técnico(a) em Geotecnologia","Técnico(a) em Informática (Manut. Hardware)","Técnico(a) em Informática e Proc. de Dados","Técnico(a) em Laboratório","Técnico(a) em Manutenção","Técnico(a) em Máq. de Escrever e Calculadoras","Técnico(a) em Mecânica Industrial","Técnico(a) em Mineração","Técnico(a) em Nutrição e Dietética","Técnico(a) em Pesquisa","Técnico(a) em Planej. de Manut. Elétrica","Técnico(a) em Processo (Senior)","Técnico(a) em Qualidade","Técnico(a) em Rádio, Som, Vídeo e Televisão","Técnico(a) em Radiologia (Radioterapia)","Técnico(a) em Rede Elétrica","Técnico(a) em Redes de Computadores","Técnico(a) em Refrigeração e Ar Condicionado","Técnico(a) em Segurança do Trabalho","Técnico(a) em Seguros","Técnico(a) em Suprimentos","Técnico(a) em Telecomunicações","Técnico(a) em Turismo","Técnico(a) Têxtil","Telefonista","Telemarketing","Terapeuta Ocupacional","Tesoureiro(a)","Topógrafo","Tornearia em Geral","Traçador","Tradutor(a)","Vendedor(a)","Trainee","Vendedor(a) de Passagens","Vendedor(a) Externo","Vendedor(a) Interno","Vendedor(a) Técnico","Vice-Prefeito(a)","Vigilante","Vistoriador(a)","Voluntário(a)","Web Designer ", "Web Developer","Web Master"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        bd= new BDUsuario(UsuarioActivity.this);

        passos= (TextView) findViewById(R.id.conta_passos);

        profissao = (AutoCompleteTextView) findViewById(R.id.edt_profissao);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PROFISSAO);
        profissao.setAdapter(adapter);
        input_nome= (TextInputLayout) findViewById(R.id.input_nome);
        input_email= (TextInputLayout) findViewById(R.id.input_email);
        input_tel= (TextInputLayout) findViewById(R.id.input_tel);
        input_senha= (TextInputLayout) findViewById(R.id.input_senha);
        nome= (EditText) findViewById(R.id.edt_nome);
        numero= (EditText) findViewById(R.id.edt_tel);
        //profissao= (EditText) findViewById(R.id.edt_profissao);
        email= (EditText) findViewById(R.id.edt_email);
        senha= (EditText) findViewById(R.id.edt_senha);
        txt_msg= (TextView) findViewById(R.id.txtmsg);
        btn_prox= (Button) findViewById(R.id.btn_proximo);
        btn_salvar= (Button) findViewById(R.id.btn_salvar);
        btn_ja_cadastrado= (Button) findViewById(R.id.btn_ja_cadastrado);

        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("email",email.getText().toString());
                bd.verificaEmailExiste(UsuarioActivity.this,email.getText().toString());
                if(validaCampo1Parte()){

                    //some campo nome e numero
                    email.setVisibility(View.GONE);
                    senha.setVisibility(View.GONE);
                    btn_prox.setVisibility(View.GONE);
                    passos.setText("2 de 2");
                    //aparece campo profissao e botao salvar
                    numero.setVisibility(View.VISIBLE);
                    nome.setVisibility(View.VISIBLE);
                    profissao.setVisibility(View.VISIBLE);
                    btn_salvar.setVisibility(View.VISIBLE);
                    txt_msg.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampo2Parte()) {

                   /* Usuario u= new Usuario();
                    u.setNome(nome.getText().toString());
                    u.setNumero(numero.getText().toString());
                    u.setProfissao(profissao.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setSenha(senha.getText().toString());
                    bd.inserir(u);
*/
                    if(Utilitaria.Conectado(UsuarioActivity.this)) {
                        bd.salvaServidor(UsuarioActivity.this, nome.getText().toString(), numero.getText().toString(), email.getText().toString(), "", profissao.getText().toString(), senha.getText().toString());

                        bd.fecharConexao();

                       /* Toast.makeText(UsuarioActivity.this, "Feito com sucesso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UsuarioActivity.this, MainActivity.class);
                        startActivity(i);*/
                        finish();
                    }else{
                        Toast.makeText(UsuarioActivity.this, "Sem conexão com internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_ja_cadastrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UsuarioActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public boolean validaCampo1Parte(){


        if( email.getText().toString().isEmpty() || email.getText().toString()==null || !isValidEmail(email.getText().toString().trim())){
            requestFocus(email);
            input_email.setError("Email invalido ou vazio");
            return false;
        }else{
            input_email.setErrorEnabled(false);
        }
        if( senha.getText().toString().isEmpty() || senha.getText().toString()==null) {
            requestFocus(senha);
            input_senha.setError("Campo obrigatorio");
            return false;
        }else{
            input_senha.setErrorEnabled(false);
        }


        return true;
    }
    public boolean validaCampo2Parte(){

        if( nome.getText().toString().isEmpty() || nome.getText().toString()==null){
            requestFocus(nome);
            input_nome.setError("Campo obrigatorio");
            return false;
        }else{
            input_nome.setErrorEnabled(false);
        }

        if( numero.getText().toString().isEmpty() || numero.getText().toString()==null){
            requestFocus(numero);
            input_tel.setError("Campo obrigatorio");
            return false;
        }else{
            input_tel.setErrorEnabled(false);
        }


        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
