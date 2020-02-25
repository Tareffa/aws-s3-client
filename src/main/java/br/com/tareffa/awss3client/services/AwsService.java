package br.com.tareffa.awss3client.services;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.springframework.stereotype.Service;

import br.com.tareffa.awss3client.domain.models.Bucket;

@Service
public class AwsService {

    public void upload(File file, String filename, Bucket bucket) {
        this.upload(file, filename, bucket, s3client(bucket));
    }

    public void upload(File file, String filename, Bucket bucket, AmazonS3 s3client) {
		
		try {
			PutObjectResult result = s3client.putObject(new PutObjectRequest(bucket.getBucketName(), filename, file));
			
			ObjectMetadata meta = result.getMetadata();
			
			for (Map.Entry<String, Object> entry : meta.getRawMetadata().entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}

	        System.out.println("===================== Upload File - Done! =====================");
	        
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException: ");
            System.out.println("Error Message: " + ace.getMessage());
        }
	}

    private AmazonS3 s3client(Bucket bucket) {
        return this.s3client(bucket.getAccessKeyId(), bucket.getSecretAccessKey(), bucket.getRegion());
    }
    
	private AmazonS3 s3client(String awsId, String awsKey, String region) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
        
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
								.withRegion(Regions.fromName(region))
		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                        .build();
		return s3Client;
    }
    
}
