package sistemas.distribuidos.projeto.serverless.victor.model;

/**
 * Variáveis comuns para o projeto todo.
 * 
 * @author Victor Santiago
 *
 */
public class Commons {

	public static final String TABLE_NAME = "bank_accounts";
	public static final String TABLE_ACCOUNT_ID = "account_id";
	public static final String TABLE_BALANCE_FIELD = "balance";
	
	public static final Long LOCK_LEASE_DURATION = 10L;
	public static final Long LOCK_HEARTBEAT_DURATION = 3L;
}
