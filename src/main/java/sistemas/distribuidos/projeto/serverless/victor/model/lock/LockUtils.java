package sistemas.distribuidos.projeto.serverless.victor.model.lock;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.dynamodbv2.AcquireLockOptions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBLockClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBLockClientOptions;
import com.amazonaws.services.dynamodbv2.LockItem;

import sistemas.distribuidos.projeto.serverless.victor.model.Commons;
import sistemas.distribuidos.projeto.serverless.victor.model.output.BankAccount;

/**
 * Conjunto de métodos para auxiliar no processo de Lock/Unlock de contas bancárias.
 * 
 * @author Victor Santiago
 */
public class LockUtils {

	/**
	 * Verifica se determinada conta está bloqueada ou não no DynamoDB.
	 * 
	 * @param dynamoDb CLiente do DynamoDB
	 * @param id Id da conta bancária
	 * @return V/F se a conta está bloqueada.
	 * @throws InterruptedException Caso a transação do banco seja interrompida.
	 * @throws IOException Erro comum de I/O.
	 */
	public static boolean isLocked(AmazonDynamoDB dynamoDb, int id) throws InterruptedException, IOException {
		final AmazonDynamoDBLockClient client = new AmazonDynamoDBLockClient(
	            AmazonDynamoDBLockClientOptions.builder(dynamoDb, Commons.TABLE_NAME)
	                    .build());
		
		final Optional<LockItem> item = client.getLock(Commons.TABLE_ACCOUNT_ID, Optional.empty());
		
        client.close();
        
        return !item.get().isExpired();
	}
	
	/**
	 * Bloqueia ou não uma conta, de acordo com o booleano "lock".
	 * O bloqueio expira automaticamente depois dos segundos especificados em LOCK_LEASE_DURATION.
	 * 
	 * @param dynamoDb Cliente do Dynamo.
	 * @param id ID da conta bancária.
	 * @param lock Se deve bloquear ou não uma conta.
	 * @throws InterruptedException É jogada caso a transação do banco seja interrompida.
	 * @throws IOException Erro de I/O comum.
	 */
	public static void lock(AmazonDynamoDB dynamoDb, int id, boolean lock) throws InterruptedException, IOException {
		final AmazonDynamoDBLockClient client = new AmazonDynamoDBLockClient(
	            AmazonDynamoDBLockClientOptions.builder(dynamoDb, Commons.TABLE_NAME)
	                    .withTimeUnit(TimeUnit.SECONDS)
	                    .withLeaseDuration(Commons.LOCK_LEASE_DURATION)
	                    .build());
		
		final Optional<LockItem> item = client.getLock(id+"", Optional.empty());

        client.close();

		if(lock) {
	        final Optional<LockItem> lockItem =
	                client.tryAcquireLock(AcquireLockOptions.builder(id+"").build());	
		} else {
			client.releaseLock(item.get());
		}		
        
	}
	
	/**
	 * Helper para criar objeto de conta bloqueada.
	 * 
	 * @param id Id da conta bancária.
	 * @return Objeto de conta bancária bloqueada com a ID especificada.
	 */
	public static BankAccount lockedAccount(int id) {
		return new BankAccount(id, -1, true);
	}

}
