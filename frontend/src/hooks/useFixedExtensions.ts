import { useCallback, useEffect } from 'react'
import { useAppDispatch } from '@/hooks/useAppDispatch'
import { useAppSelector } from '@/hooks/useAppSelector'
import type { RootState } from '@/store'
import {
  fetchFixedExtensions,
  toggleFixed,
} from '@/store/slices/fixedExtensionsSlice'

export const useFixedExtensions = () => {
  const dispatch = useAppDispatch()

  const fixedList = useAppSelector((state: RootState) => state.fixedExtensions.list)
  const loading = useAppSelector((state: RootState) => state.fixedExtensions.loading)
  const error = useAppSelector((state: RootState) => state.fixedExtensions.error)

  const fetch = useCallback(() => {
    dispatch(fetchFixedExtensions())
  }, [dispatch])

  const toggle = useCallback(
    async (id: number) => {
      try {
        const result = await dispatch(toggleFixed(id)).unwrap()
        if (!result) {
          console.warn(`[toggle] ID ${id} 응답이 null이므로 상태 변경 생략`)
        }
      } catch (err) {
        console.error('토글 실패:', err)
      }
    },
    [dispatch]
  )

  useEffect(() => {
    fetch()
  }, [fetch])

  return {
    fixedList,
    loading,
    error,
    toggle,
  }
}
