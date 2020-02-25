package br.com.tareffa.awss3client.services;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.springframework.stereotype.Service;

import br.com.tareffa.awss3client.domain.models.Bucket;

@Service
public class AwsService {

	@Deprecated
	public void upload(File file, String key, Bucket bucket) {
		this.upload(key, file, bucket);
	}

    public void upload(String key, File file, Bucket bucket) {
		AmazonS3 s3client = s3client(bucket);

		try {
			// envia o objeto para o s3.
			putObject(key, file, bucket, s3client);

			// busca o objeto do s3
			S3Object object = getObject(key, bucket, s3client);

			System.out.println("Object Key  ................. " + object.getKey());
			System.out.println("Object Redirect Location  ... " + object.getRedirectLocation());

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

	public void upload(File file, String filename, Bucket bucket, AmazonS3 s3client) {
		
		try {
			PutObjectResult result = s3client.putObject(new PutObjectRequest(bucket.getBucketName(), filename, file));
			
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


	private S3Object getObject(String key, Bucket bucket, AmazonS3 s3client) {
		return s3client.getObject(new GetObjectRequest(bucket.getBucketName(), key));
	}

	private PutObjectResult putObject(String key, File file, Bucket bucket, AmazonS3 s3client) 
			throws AmazonServiceException, AmazonClientException {
		return s3client.putObject(
			new PutObjectRequest(bucket.getBucketName(), key, file)
				.withCannedAcl(CannedAccessControlList.PublicRead)
		);
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
