-- amount 를 int 상한(약 21억)을 넘을 수 있는 값에도 대응하도록 BIGINT 로 확장
-- (Account.java 의 amount 필드를 long 으로 바꾼 것과 짝을 이루는 DB 변경)
ALTER TABLE account MODIFY COLUMN amount BIGINT NOT NULL;

-- type 에 income/expense 외 값이 들어가는 것을 DB 레벨에서 차단
ALTER TABLE account ADD CONSTRAINT chk_account_type CHECK (type IN ('income', 'expense'));

-- 날짜 단일 조회, (구분, 날짜) 조합 조회 각각을 위한 인덱스
CREATE INDEX idx_account_date ON account (account_date);
CREATE INDEX idx_account_type_date ON account (type, account_date);

-- 주의: 현재 AccountDAO 의 쿼리들은 DATE_FORMAT(account_date, '%Y-%m') = ? 형태로
-- account_date 컬럼에 함수를 적용해서 비교한다. 컬럼에 함수를 적용하면 옵티마이저가
-- 위 인덱스를 타지 못하고 풀 스캔하게 된다. 실제로 인덱스를 타게 하려면
--   account_date >= ? AND account_date < DATE_ADD(?, INTERVAL 1 MONTH)
-- 같은 범위 조건으로 쿼리를 바꿔야 한다. 이번 변경에서는 인덱스만 추가하고
-- 쿼리는 그대로 두었다 (쿼리 변경은 별도 작업으로 분리).
