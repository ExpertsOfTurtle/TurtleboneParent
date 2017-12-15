var PROFILE = {
	username : null,
	tokenId : null
}
function appendQueryURLForRequest() {
	return "?username=" + PROFILE.username + "&tokenId=" + PROFILE.tokenId;
}