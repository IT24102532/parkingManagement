 const validatePassword  =  ( ) =>  {

    const password =  document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");



    if  ( password === confirmPassword)
    {
        alert("password do not   match");
        return false;
    }
}