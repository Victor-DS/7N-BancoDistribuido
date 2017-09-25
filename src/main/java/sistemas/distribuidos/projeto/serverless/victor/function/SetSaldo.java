package sistemas.distribuidos.projeto.serverless.victor.function;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sistemas.distribuidos.projeto.serverless.victor.model.Commons;
import sistemas.distribuidos.projeto.serverless.victor.model.input.SetSaldoInput;
import sistemas.distribuidos.projeto.serverless.victor.model.lock.LockUtils;
import sistemas.distribuidos.projeto.serverless.victor.model.output.BankAccount;

/**
 * Victor Santiago
 * TIA 41357205
 * 
 * Função Lambda para atualizar o saldo de uma conta bancária.
 */
public class SetSaldo implements RequestHandler<SetSaldoInput, BankAccount> {

	@Override
	public BankAccount handleRequest(SetSaldoInput input, Context arg1) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();        
		BankAccount account = new BankAccount(input.id);
        
		try {
			if(LockUtils.isLocked(client, input.id)) {
				return LockUtils.lockedAccount(input.id);
			}			
		} catch(Exception e) {
			return LockUtils.lockedAccount(input.id);
		}
		
		account.setLocked(false);
		
		try {
			LockUtils.lock(client, input.id, true);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("ProductCatalog");

		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
		expressionAttributeNames.put("#B", "balance");

		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":val", input.balance);

		UpdateItemOutcome outcome =  table.updateItem(
		    Commons.TABLE_ACCOUNT_ID,
		    input.balance,
		    "set #B = :val",
		    expressionAttributeNames,
		    expressionAttributeValues);
		
		account.setBalance(outcome.getItem().getDouble(Commons.TABLE_BALANCE_FIELD));

		return account;
	}

}
