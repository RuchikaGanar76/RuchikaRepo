package com.cg.bank.model;

public class Account {

		private int accountNo;
		private int ammount;

		
		public Account() {
			super();
		}


		public int getAccountNo() {
			return accountNo;
		}


		public void setAccountNo(int accountNo) {
			this.accountNo = accountNo;
		}


		public int getAmmount() {
			return ammount;
		}


		public void setAmmount(int ammount) {
			this.ammount = ammount;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + accountNo;
			result = prime * result + ammount;
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Account other = (Account) obj;
			if (accountNo != other.accountNo)
				return false;
			if (ammount != other.ammount)
				return false;
			return true;
		}

		
		
		
}
