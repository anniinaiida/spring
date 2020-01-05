var url = contextRoot + "tasks/random"
 
var http = new XMLHttpRequest()
// Implement the functionality to retrieve a random book here
 
http.onreadystatechange = function() {
    if (this.readyState != 4 || this.status != 200) {
        return
    }
    
    var task = JSON.parse(this.responseText)
    document.getElementById("title").innerHTML = task.name
}
 
http.onreadystatechange = function() {
    if (this.readyState != 4) {
        return
    }
    
    document.getElementById("received").innerHTML = this.responseText
}
 
function showTasks() {
    http.open("GET", url)
    http.send()
}