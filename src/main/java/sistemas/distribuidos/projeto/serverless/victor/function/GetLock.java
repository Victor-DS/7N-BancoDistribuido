package sistemas.distribuidos.projeto.serverless.victor.function;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sistemas.distribuidos.projeto.serverless.victor.model.input.LockInput;
import sistemas.distribuidos.projeto.serverless.victor.model.lock.LockUtils;
import sistemas.distribuidos.projeto.serverless.victor.model.output.LockOutput;

/**
 * Victor Santiago
 * TIA 41357205
 * 
 * Função Lambda para saber se tal conta está bloqueada ou não.
 */
public class GetLock implements RequestHandler<LockInput, LockOutput> {

	@Override
	public LockOutput handleRequest(LockInput input, Context arg1) {
        AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.standard().build();

        LockOutput lock = new LockOutput();
		try {
			lock.isLocked = LockUtils.isLocked(dynamoDb, input.id);
		} catch (Exception e) {
			lock.isLocked = true;
		}
		
		return lock;
	}

}
