package br.cin.ufpe.evaluationManager.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public class EvaluationConf implements Serializable
{
    public static final int CODE_CANCELLED = -1;
    public static final int CODE_CREATED   = 0;
    public static final int CODE_MODELLDED_APP = 1;
    public static final int CODE_MODELLDED_NET = 2;
    public static final int CODE_EVALUATED_APP = 3;
    public static final int CODE_EVALUATED_NET = 4;
    
    private long evaluateId;            // identificacao da avaliacao
    private long userId;                // identificacao do usuario
    private long projectId;             // identificacao do projeto
    
    private int code;                   // código do status
    
    private Map<String,String> configuration;   // configuracao
    private List<EvaluationStatus> status;        // status
    
    public EvaluationConf()
    {
        configuration = new HashMap<>();
        status = new LinkedList<>();
    }

    public long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public List<EvaluationStatus> getStatus() {
        return status;
    }

    public void setStatus(List<EvaluationStatus> status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    /**                          Configurações
     * 
     * "both_evaluate"        : indica que deve avaliar uma aplicacao e uma rede
     * 
     * "application_evaluate"   : indica que deve avaliar uma aplicacao
     * "application_model_name" : nome do modelo da aplicacao
     * "application_function"   : indica a funcao que deve ser avaliada
     * "application_path"       : caminho da pasta da aplicacao para ser modelado
     * 
     * "network_evaluate"       : indica que deve avaliar uma rede
     * "network_model_name"     : nome do modelo da aplicao
     * "network_reliability"    : se deve avaliar a confiabilidade da rede
     * "network_path"           : caminho do arquivo da rede para ser modelado
     * 
     * "sensibility_evaluate"   : faz uma analise de sensibilidade
     * "sensibility_parent_id"  : indica a avaliacao pai da sensibilidade
     */
}
