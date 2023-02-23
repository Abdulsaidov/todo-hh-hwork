export const TodoStore = class extends EventTarget {
	constructor() {
	  super();
	  this.todos = [];
	  this._readTodos();
	}
  
	_readTodos() {
	  fetch('http://127.0.0.1:8080/api/task/all')  
		.then(response => {
			console.log("read",response);
			return response.json();
		})
		.then(data => {
			console.log(data)
		  	this.todos = data;
		  	this.dispatchEvent(new CustomEvent('save'));
		})
		.catch(error => console.error('Error reading todos', error));
	}
  
	_save() {
		this.dispatchEvent(new CustomEvent('save'));
	}
  
	// GETTER methods
	get(id) {		
		return this.todos.find((todo) => todo.id === Number(id))
	}
  
	isAllCompleted() {
	  return this.todos.every(todo => todo.completed);
	}
  
	hasCompleted() {
	  return this.todos.some(todo => todo.completed);
	}
  
	all(filter) {
	  if (filter === 'active') {
		return this.todos.filter(todo => !todo.completed);
	  } else if (filter === 'completed') {
		return this.todos.filter(todo => todo.completed);
	  } else {
		return this.todos;
	  }
	}
  
	// MUTATE methods
	add(title) {
		console.log("test",title);
		fetch('http://127.0.0.1:8080/api/task/add',{
			method:'POST',
			headers: {
				'Content-Type': 'application/json'
			  },
			body: title
		}).then(response => {
			console.log('add',response);
			if (!response.ok) {
				throw new Error(`Error saving todos (${response.status})`);
			} 
			return response.json();
		}).then(data => {
			console.log('todo from db',data);
			this.todos.push(data);
			this._save();
		}).catch(error => console.error('Error saving todos', error))
		console.log(this.todos)
	}
  
	remove({id}) {
		this.todos = this.todos.filter((todo) => todo.id !== id);
		console.log('delete id', id)
		fetch('http://127.0.0.1:8080/api/task/delete/' + id, {
			method:'DELETE',
			headers: {
				'Content-Type': 'application/json'
			  },
		}).then(response => {
			console.log('delete',response);
			if (!response.ok) {
				throw new Error(`Error deleting todos (${response.status})`);
			} 
			this._save();
			return response.json();
		}).catch(error => console.error('Error deleting todos', error))
		console.log(this.todos)
	}
  
	toggle({ id }) {
	  const todo = this.todos.find(todo => todo.id === Number(id));
	  todo.completed = !todo.completed;
	  this.update(todo);
	}
  
	clearCompleted() {
		fetch('http://127.0.0.1:8080/api/task/clear',{
			method:'DELETE',
			headers: {
				'Content-Type': 'application/json'
			  },
		})  
		.then(response => {
			if (!response.ok) {
				throw new Error(`Error clear completed todos (${response.status})`);
			} 
			
		})
		.catch(error => console.error('Error clear completed  todos', error));
		this.todos = this.todos.filter((todo) => !todo.completed);
		this._save();
	}
  
	update(todo) {
	  const index = this.todos.findIndex(t => t.id === todo.id);
	  this.todos[index] = todo;
	  fetch('http://127.0.0.1:8080/api/task/update/' + todo.id, {
		method:'PATCH',
		headers: {
			'Content-Type': 'application/json'
		  },
		body: JSON.stringify(todo)
	}).then(response => {
		console.log('update',response);
		if (!response.ok) {
			throw new Error(`Error update todos (${response.status})`);
		} 
		return response.json();
	})
	.catch(error => console.error('Error update todos', error))
	  this._save();
	}
  
	toggleAll() {
		const completed = !this.hasCompleted() || !this.isAllCompleted();
		this.todos = this.todos.map((todo) => ({ ...todo, completed }));
		this.todos.forEach((todo)=>this.update(todo));
		this._save();
	}
  
	revert() {
	  this._readTodos();
	}
  };
  