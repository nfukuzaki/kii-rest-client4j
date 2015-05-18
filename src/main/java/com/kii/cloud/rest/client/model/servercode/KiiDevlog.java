package com.kii.cloud.rest.client.model.servercode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiDevlog extends KiiJsonModel implements Comparable<KiiDevlog> {
	
	public enum LogLevel {
		DEBUG,
		INFO,
		WARN,
		ERROR
	}
	
	public enum LogKey {
		DEFAULT("default", "${time} [${level}] ${key} description:${description}"),
		USER_REGISTER("user.register", "${time} [${level}] ${key} description:${description} userID:${userID} login-name:${loginName} email-address:${emailAddress} phone-number:${phoneNumber} thirdParty:${thirdPartyAccountType} thirdPartyID:${thirdPartyID}"),
		USER_LOGIN("user.login", "${time} [${level}] ${key} description:${description} userID:${userID} login-name:${loginName}"),
		USER_FACEBOOK_LINK("user.facebook.link", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_TWITTER_LINK("user.twitter.link", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_FACEBOOK_UNLINK("user.facebook.unlink", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_TWITTER_UNLINK("user.twitter.unlink", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_3RDPARTY_LINK("user.3rdparty.link", "${time} [${level}] ${key} description:${description} userID:${userID} thirdParty:${thirdPartyAccountType}"),
		USER_3RDPARTY_UNLINK("user.3rdparty.unlink", "${time} [${level}] ${key} description:${description} userID:${userID} thirdParty:${thirdPartyAccountType}"),
		USER_PASSWORD_CHANGE("user.password.change", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_PASSWORD_RESET("user.password.reset", "${time} [${level}] ${key} description:${description} userID:${userID} account-type: ${accountType}"),
		USER_UPDATE("user.update", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_REMOVE("user.remove", "${time} [${level}] ${key} description:${description} userID:${userID}"),
		USER_EMAIL_VERIFY("user.email.verify", "${time} [${level}] ${key} description:${description} userID:${userID} email-address:${emailAddress}"),
		USER_PHONE_VERIFY("user.phone.verify", "${time} [${level}] ${key} description:${description} userID:${userID} phone-number:${phoneNumber}"),
		GROUP_CREATE("group.create", "${time} [${level}] ${key} description:${description} groupID:${groupID}"),
		GROUP_NAME_CHANGE("group.name.change", "${time} [${level}] ${key} description:${description} groupID:${groupID} new-name:${newGroupName}"),
		GROUP_MEMBER_ADD("group.member.add", "${time} [${level}] ${key} description:${description} groupID:${groupID} new-member:${newMember} failed-to-add:${failedToAdd}"),
		GROUP_MEMBER_REMOVE("group.member.remove", "${time} [${level}] ${key} description:${description} groupID:${groupID} remove-member:${removedMember}"),
		GROUP_DELETE("group.delete", "${time} [${level}] ${key} description:${description} groupID:${groupID}"),
		BUCKET_APP_CREATE("bucket.app.create", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType}"),
		BUCKET_GROUP_CREATE("bucket.group.create", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType}"),
		BUCKET_USER_CREATE("bucket.user.create", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType}"),
		BUCKET_APP_OBJECT_CREATE("bucket.app.object.create", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType}"),
		BUCKET_GROUP_OBJECT_CREATE("bucket.group.object.create", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType}"),
		BUCKET_USER_OBJECT_CREATE("bucket.user.object.create", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType}"),
		BUCKET_APP_OBJECT_RETRIEVE("bucket.app.object.retrieve", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_GROUP_OBJECT_RETRIEVE("bucket.group.object.retrieve", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_USER_OBJECT_RETRIEVE("bucket.user.object.retrieve", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_APP_OBJECT_UPDATE("bucket.app.object.update", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType} trashed:${trashed}"),
		BUCKET_GROUP_OBJECT_UPDATE("bucket.group.object.update", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType} trashed:${trashed}"),
		BUCKET_USER_OBJECT_UPDATE("bucket.user.object.update", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} data-type:${dataType} trashed:${trashed}"),
		BUCKET_APP_OBJECT_QUERY("bucket.app.object.query", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} query:${query} results-count:${resultsCount}"),
		BUCKET_GROUP_OBJECT_QUERY("bucket.group.object.query", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} query:${query} results-count:${resultsCount}"),
		BUCKET_USER_OBJECT_QUERY("bucket.user.object.query", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} query:${query} results-count:${resultsCount}"),
		BUCKET_APP_OBJECTVDELETE("bucket.app.object.delete", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_GROUP_OBJECT_DELETE("bucket.group.object.delete", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_USER_OBJECT_DELETE("bucket.user.object.delete", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_APP_OBJECT_BODY_UPLOAD("bucket.app.object.body.upload", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash}"),
		BUCKET_GROUP_OBJECT_BODY_UPLOAD("bucket.group.object.body.upload", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash}"),
		BUCKET_USER_OBJECT_BODY_UPLOAD("bucket.user.object.body.upload", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash}"),
		BUCKET_APP_OBJECT_BODY_UPLOAD_TERMINATE("bucket.app.object.body.upload.terminate", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} uploadID:${uploadID}"),
		BUCKET_GROUP_OBJECT_BODY_UPLOAD_TERMINATE("bucket.group.object.body.upload.terminate", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} uploadID:${uploadID}"),
		BUCKET_USER_OBJECT_BODY_UPLOAD_TERMINATE("bucket.user.object.body.upload.terminate", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} uploadID:${uploadID}"),
		BUCKET_APPVOBJECT_BODY_CHUNK_UPLOAD("bucket.app.object.body.chunk.upload", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash} from:${from} to:${to} total:${total}"),
		BUCKET_GROUP_OBJECT_BODY_CHUNK_UPLOAD("bucket.group.object.body.chunk.upload", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash} from:${from} to:${to} total:${total}"),
		BUCKET_USERVOBJECT_BODY_CHUNK_UPLOAD("bucket.user.object.body.chunk.upload", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} body-data-type:${bodyDataType} uploadID:${uploadID} client-hash:${clientHash} from:${from} to:${to} total:${total}"),
		BUCKET_APP_OBJECT_BODYVPUBLISH("bucket.app.object.body.publish", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} ${expires}"),
		BUCKET_GROUP_OBJECT_BODY_PUBLISH("bucket.group.object.body.publish", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} ${expires}"),
		BUCKET_USER_OBJECT_BODY_PUBLISH("bucket.user.object.body.publish", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} expires:${expires}"),
		BUCKET_APP_OBJECT_BODY_DELETE("bucket.app.object.body.delete", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_GROUP_OBJECT_BODY_DELETE("bucket.group.object.body.delete", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_USER_OBJECT_BODY_DELETE("bucket.user.object.body.delete", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID}"),
		BUCKET_APP_ACLVGRANT("bucket.app.acl.grant", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_GROUP_ACL_GRANT("bucket.group.acl.grant", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_USERVACL_GRANT("bucket.user.acl.grant", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_APP_ACLVREVOKE("bucket.app.acl.revoke", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_GROUPVACL_REVOKE("bucket.group.acl.revoke", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_USER_ACL_REVOKE("bucket.user.acl.revoke", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} verb:${verb} type:${type}"),
		BUCKET_APP_OBJECT_ACL_GRANT("bucket.app.object.acl.grant", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_GROUP_OBJECT_ACL_GRANT("bucket.group.object.acl.grant", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_USER_OBJECT_ACL_GRANT("bucket.user.object.acl.grant", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_APP_OBJECT_ACL_REVOKE("bucket.app.object.acl.revoke", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_GROUP_OBJECT_ACL_REVOKE("bucket.group.object.acl.revoke", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_USER_OBJECT_ACL_REVOKE("bucket.user.object.acl.revoke", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} objectID:${objectID} verb:${verb}"),
		BUCKET_APP_DELETE("bucket.app.delete", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} bucket-type:${bucketType} deleted-count:${deletedCount}"),
		BUCKET_GROUP_DELETE("bucket.group.delete", "${time} [${level}] ${key} description:${description} groupID:${groupID} bucketID:${bucketID} bucket-type:${bucketType} deleted-count:${deletedCount}"),
		BUCKET_USER_DELETE("bucket.user.delete", "${time} [${level}] ${key} description:${description} userID:${userID} bucketID:${bucketID} bucket-type:${bucketType} deleted-count:${deletedCount}"),
		OBJECT_APP_SCOPE_ACL_GRANT("object.app.scope.acl.grant", "${time} [${level}] ${key} description:${description} verb:${verb} type:${type}"),
		OBJECT_GROUP_SCOPE_ACL_GRANT("object.group.scope.acl.grant", "${time} [${level}] ${key} description:${description} groupID:${groupID} verb:${verb} type:${type}"),
		OBJECT_USER_SCOPE_ACL_GRANT("object.user.scope.acl.grant", "${time} [${level}] ${key} description:${description} userID:${userID} verb:${verb} type:${type}"),
		OBJECT_APP_SCOPE_ACL_REVOKE("object.app.scope.acl.revoke", "${time} [${level}] ${key} description:${description} verb:${verb} type:${type}"),
		OBJECT_GROUP_SCOPE_ACL_REVOKE("object.group.scope.acl.revoke", "${time} [${level}] ${key} description:${description} groupID:${groupID} verb:${verb} type:${type}"),
		OBJECT_USER_SCOPE_ACL_REVOKE("object.user.scope.acl.revoke", "${time} [${level}] ${key} description:${description} userID:${userID} verb:${verb} type:${type}"),
		PUSH_ACL_GRANT("push.acl.grant", "${time} [${level}] ${key} description:${description} topicID:${topicID} verb:${verb} type:${type}"),
		PUSH_ACL_REVOKE("push.acl.revoke", "${time} [${level}] ${key} description:${description} topicID:${topicID} verb:${verb} type:${type}"),
		DEVICE_INSTALL("device.install", "${time} [${level}] ${key} description:${description} userID:${userID} device-type:${installationType} installationID:${installationID} development:${development}"),
		DEVICE_UNINSTALL("device.uninstall", "${time} [${level}] ${key} description:${description} userID:${userID} device-type:${installationType} installationID:${installationID} development:${development}"),
		PUSH_BUCKET_SUBSCRIBE("push.bucket.subscribe", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} type:${type} filter-id:${filterID} subject:${subject}"),
		PUSH_BUCKET_UNSUBSCRIBE("push.bucket.unsubscribe", "${time} [${level}] ${key} description:${description} bucketID:${bucketID} type:${type} filter-id:${filterID} subject:${subject}"),
		PUSH_TOPIC_APP_CREATE("push.topic.app.create", "${time} [${level}] ${key} description:${description} topicID:${topicID}"),
		PUSH_TOPIC_GROUP_CREATE("push.topic.group.create", "${time} [${level}] ${key} description:${description} groupID:${groupID} topicID:${topicID}"),
		PUSH_TOPIC_USER_CREATE("push.topic.user.create", "${time} [${level}] ${key} description:${description} userID:${userID} topicID:${topicID}"),
		PUSH_TOPIC_APP_DELETE("push.topic.app.delete", "${time} [${level}] ${key} description:${description} topicID:${topicID}"),
		PUSH_TOPIC_GROUP_DELETE("push.topic.group.delete", "${time} [${level}] ${key} description:${description} groupID:${groupID} topicID:${topicID}"),
		PUSH_TOPIC_USER_DELETE("push.topic.user.delete", "${time} [${level}] ${key} description:${description} userID:${userID} topicID:${topicID}"),
		PUSH_TOPIC_APP_SUBSCRIBE("push.topic.app.subscribe", "${time} [${level}] ${key} description:${description} topicID:${topicID} subject:${subject}"),
		PUSH_TOPIC_GROUP_SUBSCRIBE("push.topic.group.subscribe", "${time} [${level}] ${key} description:${description} groupID:${groupID} topicID:${topicID} subject:${subject}"),
		PUSH_TOPIC_USER_SUBSCRIBE("push.topic.user.subscribe", "${time} [${level}] ${key} description:${description} userID:${userID} topicID:${topicID} subject:${subject}"),
		PUSH_TOPIC_APP_UNSUBSCRIBE("push.topic.app.unsubscribe", "${time} [${level}] ${key} description:${description} topicID:${topicID} subject:${subject}"),
		PUSH_TOPIC_GROUP_UNSUBSCRIBE("push.topic.group.unsubscribe", "${time} [${level}] ${key} description:${description} groupID:${groupID} topicID:${topicID} subject:${subject}"),
		PUSH_TOPIC_USER_UNSUBSCRIBE("push.topic.user.unsubscribe", "${time} [${level}] ${key} description:${description} userID:${userID} topicID:${topicID} subject:${subject}"),
		PUSH_SEND("push.send", "${time} [${level}] ${key} description:${description} sender:${sender} origin:${origin} messageID:${messageID} type:${type} succeeded-endpoints:${succeededEndpoints} failed-endpoints:${failedEndpoints}"),
		SERVERCODE_BUNDLE_DEPLOY("servercode.bundle.deploy", "${time} [${level}] ${key} description:${description} data-type:${dataType} versionID:${versionID}"),
		SERVERCODE_FILE_DEPLOY("servercode.file.deploy", "${time} [${level}] ${key} description:${description} data-type:${dataType} versionID:${versionID}"),
		SERVERCODE_DELETE("servercode.delete", "${time} [${level}] ${key} description:${description} versionID:${versionID}"),
		SERVERCODE_INVOKE("servercode.invoke", "${time} [${level}] ${key} description:${description} versionID:${versionID} args:${args} endpoint:${endpoint} auth-header:${authHeader} response-type:${responseType} response-step:${responseStep}"),
		SERVERCODE_LOG("servercode.log", "${time} [${level}] ${key} description:${description} entry-name:${entry-name}");
		
 		public static LogKey fromKey(String key) {
 			for (LogKey logKey : values()) {
 				if (logKey.getKey().equals(key)) {
 					return logKey;
 				}
 			}
 			return LogKey.DEFAULT;
 		}
		
		private final String key;
		private final String format;
		private LogKey(String key, String format) {
			this.key = key;
			this.format = format;
		}
		public String getKey() {
			return key;
		}
		public String getFormat() {
			return format;
		}
	}
	
	public static final SimpleDateFormat LOG_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static final KiiJsonProperty<String> PROPERTY_ID = new KiiJsonProperty<String>(String.class, "_id");
	public static final KiiJsonProperty<String> PROPERTY_KEY = new KiiJsonProperty<String>(String.class, "key");
	public static final KiiJsonProperty<String> PROPERTY_TIME = new KiiJsonProperty<String>(String.class, "time");
	public static final KiiJsonProperty<String> PROPERTY_LEVEL = new KiiJsonProperty<String>(String.class, "level");
	public static final KiiJsonProperty<String> PROPERTY_APP_ID = new KiiJsonProperty<String>(String.class, "appID");
	public static final KiiJsonProperty<String> PROPERTY_DESCRIPTION = new KiiJsonProperty<String>(String.class, "description");
	public static final KiiJsonProperty<String> PROPERTY_BUCKET_TYPE = new KiiJsonProperty<String>(String.class, "bucketType");
	public static final KiiJsonProperty<String> PROPERTY_BUCKET_ID = new KiiJsonProperty<String>(String.class, "bucketID");
	public static final KiiJsonProperty<String> PROPERTY_USER_ID = new KiiJsonProperty<String>(String.class, "userID");
	public static final KiiJsonProperty<String> PROPERTY_OBJECT_ID = new KiiJsonProperty<String>(String.class, "objectID");
	public static final KiiJsonProperty<String> PROPERTY_SUBJECT_USER_ID = new KiiJsonProperty<String>(String.class, "");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	public static final KiiJsonProperty<String> PROPERTY_THIRD_PARTY_ACCOUNT_TYPE = new KiiJsonProperty<String>(String.class, "thirdPartyAccountType");
	public static final KiiJsonProperty<String> PROPERTY_GROUP_NAME = new KiiJsonProperty<String>(String.class, "groupName");
	public static final KiiJsonProperty<String> PROPERTY_GROUP_ID = new KiiJsonProperty<String>(String.class, "groupID");
	public static final KiiJsonProperty<String> PROPERTY_THIRD_PARTY_ID = new KiiJsonProperty<String>(String.class, "thirdPartyID");
	public static final KiiJsonProperty<String> PROPERTY_EMAIL_ADDRESS = new KiiJsonProperty<String>(String.class, "emailAddress");
	public static final KiiJsonProperty<String> PROPERTY_PHONE_NUMBER = new KiiJsonProperty<String>(String.class, "phoneNumber");
	public static final KiiJsonProperty<String> PROPERTY_LOGIN_NAME = new KiiJsonProperty<String>(String.class, "loginName");
	public static final KiiJsonProperty<String> PROPERTY_TOPIC_ID = new KiiJsonProperty<String>(String.class, "topicID");
	public static final KiiJsonProperty<String> PROPERTY_SUBJECT = new KiiJsonProperty<String>(String.class, "subject");
	public static final KiiJsonProperty<String> PROPERTY_DATA_TYPE = new KiiJsonProperty<String>(String.class, "dataType");
	public static final KiiJsonProperty<String> PROPERTY_VERSION_ID = new KiiJsonProperty<String>(String.class, "versionID");
	public static final KiiJsonProperty<String> PROPERTY_ENTRY_NAME = new KiiJsonProperty<String>(String.class, "entry-name");
	public static final KiiJsonProperty<String> PROPERTY_ENDPOINT = new KiiJsonProperty<String>(String.class, "endpoint");
	public static final KiiJsonProperty<String> PROPERTY_AUTH_HEADER = new KiiJsonProperty<String>(String.class, "authHeader");
	public static final KiiJsonProperty<String> PROPERTY_RESPONSE_TYPE = new KiiJsonProperty<String>(String.class, "responseType");
	public static final KiiJsonProperty<String> PROPERTY_RESPONSE_STEP = new KiiJsonProperty<String>(String.class, "responseStep");
	public static final KiiJsonProperty<String> PROPERTY_TRASHED = new KiiJsonProperty<String>(String.class, "trashed");
	public static final KiiJsonProperty<String> PROPERTY_QUERY = new KiiJsonProperty<String>(String.class, "query");
	public static final KiiJsonProperty<String> PROPERTY_RESULTS_COUNT = new KiiJsonProperty<String>(String.class, "resultsCount");
	public static final KiiJsonProperty<String> PROPERTY_BODY_DATA_TYPE = new KiiJsonProperty<String>(String.class, "bodyDataType");
	public static final KiiJsonProperty<String> PROPERTY_UPLOAD_ID = new KiiJsonProperty<String>(String.class, "uploadID");
	public static final KiiJsonProperty<String> PROPERTY_CLIENT_HASH = new KiiJsonProperty<String>(String.class, "clientHash");
	public static final KiiJsonProperty<String> PROPERTY_FROM = new KiiJsonProperty<String>(String.class, "from");
	public static final KiiJsonProperty<String> PROPERTY_TO = new KiiJsonProperty<String>(String.class, "to");
	public static final KiiJsonProperty<String> PROPERTY_TOTAL = new KiiJsonProperty<String>(String.class, "total");
	public static final KiiJsonProperty<String> PROPERTY_VERB = new KiiJsonProperty<String>(String.class, "verb");
	public static final KiiJsonProperty<String> PROPERTY_DELETED_COUNT = new KiiJsonProperty<String>(String.class, "deletedCount");
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_TYPE = new KiiJsonProperty<String>(String.class, "installationType");
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_ID = new KiiJsonProperty<String>(String.class, "installationID");
	public static final KiiJsonProperty<String> PROPERTY_DEVELOPMENT = new KiiJsonProperty<String>(String.class, "development");
	public static final KiiJsonProperty<String> PROPERTY_FILTER_ID = new KiiJsonProperty<String>(String.class, "filterID");
	
	public KiiDevlog(JsonObject json) {
		super(json);
	}
	public String getID() {
		return PROPERTY_ID.get(this.json);
	}
	public LogKey getLogKey() {
		return LogKey.fromKey(PROPERTY_KEY.get(this.json));
	}
	public Date getTime() {
		try {
			return LOG_DATE_FORMATTER.parse(PROPERTY_TIME.get(this.json));
		} catch (ParseException e) {
			return null;
		}
	}
	@Override
	public String toString() {
		String s = this.getLogKey().getFormat();
		for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
			s = s.replace("${" + entry.getKey() + "}", entry.getValue().getAsString());
		}
		return s;
	}
	@Override
	public int compareTo(KiiDevlog o) {
		return this.getTime().compareTo(o.getTime());
	}
}
