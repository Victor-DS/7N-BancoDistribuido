package sistemas.distribuidos.projeto.serverless.victor.function;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sistemas.distribuidos.projeto.serverless.victor.model.Commons;
import sistemas.distribuidos.projeto.serverless.victor.model.input.GetSaldoInput;
import sistemas.distribuidos.projeto.serverless.victor.model.lock.LockUtils;
import sistemas.distribuidos.projeto.serverless.victor.model.output.BankAccount;

/**
 * Victor Santiago
 * TIA 41357205
 * 
 * Função Lambda para obter o Saldo de uma conta bancária.
 */
public class GetSaldo implements RequestHandler<GetSaldoInput, BankAccount> {
	
	@Override
	public BankAccount handleRequest(GetSaldoInput input, Context context) {
        AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard().build();
		BankAccount account = new BankAccount(input.id);
        
		try {
			if(LockUtils.isLocked(dynamoDb, input.id)) {
				return LockUtils.lockedAccount(input.id);
			}			
		} catch(Exception e) {
			return LockUtils.lockedAccount(input.id);
		}
		
		account.setLocked(false);
		
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put(Commons.TABLE_ACCOUNT_ID, new AttributeValue().withN(input.id+""));
		Map<String, AttributeValue> item = dynamoDb.getItem(new GetItemRequest()
		        .withTableName(Commons.TABLE_NAME)
		        .withKey(key))
		        .getItem();

		account.setBalance(Double.parseDouble(item.get(Commons.TABLE_BALANCE_FIELD).getN()));
		
		return account;
	}
		
}
