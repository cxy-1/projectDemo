import axios from 'axios'
export function communication(obj){
   const getToken = localStorage.getItem('setToken');
   const postData = obj.postData != undefined ?  obj.postData : {};
   const isToken = obj.isToken != undefined ? obj.isToken : true;
   const _token = isToken == true ? getToken : null;
   const isGet = obj.isGet != undefined ? obj.isGet : null;
   const instance =  axios.create({
      headers: {
         'contentType': 'application/json',
         'token':_token
      },
      // baseURL:'http://192.168.8.135:13000',
      // baseURL:'http://192.168.8.120:8088',
      withCredentials: true
   });
   if (isGet == true) {
       return instance.get(obj.url).then((res) => {
           if(res.data.code == 1100){
               window.myVue.$router.push('/login')
               return ;
           }else if(res.data.code == 401){
               window.myVue.$router.push('/')
               return ;
           }
         return Promise.resolve(res.data)
      })
   } 

   return instance.post(obj.url, postData).then((res) => {
      if(res.data.code == 1100){
         window.myVue.$router.push('/login')
         return ;
      }else if(res.data.code == 401){
         window.myVue.$router.push('/')
         return ;
      }

      return Promise.resolve(res.data)
   })
}
