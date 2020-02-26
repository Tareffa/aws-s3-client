package br.com.tareffa.awss3client.services;

import java.io.File;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import br.com.tareffa.awss3client.domain.models.Bucket;
import br.com.tareffa.awss3client.utils.FileUtils;

@Service
public class AwsService {

	@Deprecated
	public void upload(File file, String key, Bucket bucket) {
		this.upload(key, file, bucket);
	}

	public void upload(String key, File file, Bucket bucket) {
		AmazonS3 s3client = s3client(bucket);
		try {
			putObject(key, file, bucket, s3client);
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

	public File download(String key, String filename, Bucket bucket) throws Exception {
		AmazonS3 s3client = s3client(bucket);
		try {
			return FileUtils.createTemporaryFile(
				filename, getObject(key, bucket, s3client).getObjectContent()
			);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private S3Object getObject(String key, Bucket bucket, AmazonS3 s3client) {
		return s3client.getObject(new GetObjectRequest(bucket.getBucketName(), key));
	}

	private PutObjectResult putObject(String key, File file, Bucket bucket, AmazonS3 s3client)
			throws AmazonServiceException, AmazonClientException {
		return s3client.putObject(new PutObjectRequest(bucket.getBucketName(), key, file));
	}

	private AmazonS3 s3client(Bucket bucket) {
		return this.s3client(bucket.getAccessKeyId(), bucket.getSecretAccessKey(), bucket.getRegion());
	}

	private AmazonS3 s3client(String awsId, String awsKey, String region) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		return s3Client;
	}

}
